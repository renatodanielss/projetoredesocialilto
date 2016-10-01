package com.iliketo.aws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import HardCore.Common;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.iliketo.exception.VideoILiketoException;

/**
 * Classe com metodos de acesso ao servicos de Storage da Amazon S3
 * @author OSVALDIMAR
 *
 */
public class ILiketooBucketsBusinessAWS {

	static final Logger log = Logger.getLogger(ILiketooBucketsBusinessAWS.class);
	
	private static String bucketName = "";
	private final static String usuario = "dev@iliketoo.com.br";
	private final static String access_key_id = "AKIAJRT64JUHTJSPWZEQ";
	private final static String secret_access_key = "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";
	private final static String endpoint = "s3-us-west-2.amazonaws.com";
	
	public static final String LINK_BUCKET_PRODUCAO = "http://iliketoo.aws.midia" + "." + endpoint;
	public static final String LINK_BUCKET_DEV = "http://iliketoo.aws.midia-piloto" + "." + endpoint;
	public static final String AWS_PRODUCAO = "AWS-producao";
	public static final String AWS_DEV = "AWS-dev";
	public static final String AWS_TEMPORARIO = "temp";
	public static final String LOCAL = "local";
	
	public boolean IS_LOCAL_STORAGE_AMAZON = false;
	private AmazonS3Client amazonS3client;	
	
	/**
	 * Metodo construtor recebe String armazenamento para setar local de Storage
	 * @param localArmazenamento
	 */
	public ILiketooBucketsBusinessAWS(){
		
		//**Configurar local de armazenamento constante**
		//String localArmazenamento = AWS_PRODUCAO	//bucket de producao
		//String localArmazenamento = AWS_DEV		//bucket desenvolvimento
		//String localArmazenamento = "local";		//servidor local
		String localArmazenamento = getLocalDeArmazenamento();

		if(localArmazenamento.equalsIgnoreCase(AWS_PRODUCAO)){
			bucketName = "iliketoo.aws.midia";
			this.IS_LOCAL_STORAGE_AMAZON = true;
			this.amazonS3client = obterCredenciaisCliente();
		}
		if(localArmazenamento.equalsIgnoreCase(AWS_DEV)){
			bucketName = "iliketoo.aws.midia-piloto";
			this.IS_LOCAL_STORAGE_AMAZON = true;
			this.amazonS3client = obterCredenciaisCliente();
		}
	}
	
	/**
	 * Metodo ler arquivo properties de configuracoes para verificar o local do armazenamento.
	 */
	private String getLocalDeArmazenamento(){
		try {
			Properties prop = new Properties();
			String filename = "config/config_iliketoo.properties";
			InputStream input = this.getClass().getClassLoader().getResourceAsStream(filename);  
			if(input==null){
		        log.error("Arquivo properties de configuracoes nao encontrado: " + filename);
			    return null;
			}
			prop.load(input);
			String localArmazenamento = prop.getProperty("LOCAL_STORAGE");
			log.error("Local do armazenamento configurado: " + localArmazenamento);
			return localArmazenamento;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Construtor para teste e configuracoes da classe ILiketooBucketsBusinessAWS
	 */
	public ILiketooBucketsBusinessAWS(boolean teste, String local){		
		if(teste){
			if(local.equalsIgnoreCase(AWS_PRODUCAO)){
				bucketName = "iliketoo.aws.midia";
				this.IS_LOCAL_STORAGE_AMAZON = true;
				this.amazonS3client = obterCredenciaisCliente();
			}
			if(local.equalsIgnoreCase(AWS_DEV)){
				bucketName = "iliketoo.aws.midia-piloto";
				this.IS_LOCAL_STORAGE_AMAZON = true;
				this.amazonS3client = obterCredenciaisCliente();
			}
		}
	}

	/**
	 * Metodo configura as politicas de seguranca para o bucket
	 * @param myBucket
	 */
	public void setConfigBucketPolicy(String myBucket){
		log.info("AWS - Metodo setConfigBucketPolicy() trying...");
		String policyText = 
		"{"
		    +"\"Version\": \"2012-10-17\","
		    +"\"Id\": \"http better policy\","
		    +"\"Statement\": ["
		        +"{"
		            +"\"Sid\": \"AddPerm\","
		            +"\"Effect\": \"Allow\","
		            +"\"Principal\": \"*\","
		            +"\"Action\": [\"s3:GetObject\"],"
		            +"\"Resource\": [\"arn:aws:s3:::" +myBucket+ "/*\"]"
		        +"}"
		    +"]"
		+"}";
		this.amazonS3client.setBucketPolicy(myBucket, policyText);
		log.info("AWS - Set new config policy success bucket name: " + bucketName + " - config: " + policyText);
	}
	
	/**
	 * Metodo cria um bucket
	 */
	public void criarBucket(){
		
		log.info("AWS - Metodo criarBucket()");       
        try {
            if(!(this.amazonS3client.doesBucketExist(bucketName))){
            	// Note that CreateBucketRequest does not specify region. So bucket is 
            	// created in the region specified in the client.
            	log.info("AWS - New bucket name: " + bucketName);
            	this.amazonS3client.createBucket(new CreateBucketRequest(bucketName));
            }else{
            	log.info("AWS - Bucket name already exist: " + bucketName);
            }
            // Get location.
            String bucketLocation = this.amazonS3client.getBucketLocation(new GetBucketLocationRequest(bucketName));
            log.info("AWS - Bucket location = " + bucketLocation);
            
         } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
            throw ace;
        }
    }
	
	/**
	 * Metodo autentica a sessao client no ambiente Storage Amazon (credenciais, endpoint e regiao)
	 * @return
	 */
	private static AmazonS3Client obterCredenciaisCliente(){
		log.info("AWS - Metodo obterCredenciaisCliente()");
		ClientConfiguration cc = new ClientConfiguration()
	    .withMaxErrorRetry (15)
	    .withMaxConnections(100)
	    .withConnectionTimeout (60*60*1000);
		AmazonS3Client amazonS3client = new AmazonS3Client(new BasicAWSCredentials(access_key_id, secret_access_key), cc);
		amazonS3client.setEndpoint(endpoint);
		amazonS3client.setRegion(Region.getRegion(Regions.US_WEST_2));
		log.info("AWS - "+ amazonS3client.getS3AccountOwner());
		return amazonS3client;
	}
	
	/**
	 * Metodo faz upload de arquivos para o bucket no AWS
	 * 
	 * @param keyFilename
	 * @param fileItem
	 * @return String - nome do arquivo salvo
	 * @throws IOException
	 */
	public String uploadDeArquivosParaStorageAmazon(String keyFilename, FileItem fileItem) throws IOException{
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream input = fileItem.getInputStream();
		metadata.setContentType(fileItem.getContentType());
		metadata.setContentLength(fileItem.getSize());
		
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, null);
		log.info("AWS - Put object trying...");
		this.amazonS3client.putObject(new PutObjectRequest(bucketName, keyFilename, input, metadata));
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
		return keyFilename;
	}
	
	/**
	 * Metodo faz upload de arquivos para um diretorio do bucket no AWS
	 * 
	 * @param keyFilename
	 * @param folderName
	 * @param fileItem
	 * @return String - nome do arquivo salvo
	 * @throws IOException
	 */
	public String uploadDeArquivosParaDiretorioStorageAmazon(String keyFilename, String folderName, FileItem fileItem) throws IOException{
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream input = fileItem.getInputStream();
		metadata.setContentType(fileItem.getContentType());
		metadata.setContentLength(fileItem.getSize());
		
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, folderName);
		String pathFile = folderName +"/"+ keyFilename;
		log.info("AWS - Put object in folder trying...");
		this.amazonS3client.putObject(new PutObjectRequest(bucketName, pathFile, input, metadata));
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return keyFilename;
	}
	
	/**
	 * Metodo faz upload de arquivos para um diretorio temporario do bucket no AWS
	 * 
	 * @param keyFilename
	 * @param folderName
	 * @param fileItem
	 * @return - String nome do arquivo salvo
	 * @throws IOException
	 */
	public String uploadDeArquivosParaDiretorioTemporarioStorageAmazon(String keyFilename, String folderName, FileItem fileItem) throws IOException{
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream input = fileItem.getInputStream();
		metadata.setContentType(fileItem.getContentType());
		metadata.setContentLength(fileItem.getSize());
		
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, folderName);
		String pathFile = folderName +"/"+ keyFilename;
		log.info("AWS - Put object temporario in folder trying...");
		this.amazonS3client.putObject(new PutObjectRequest(bucketName, pathFile, input, metadata));
		log.info("AWS - Put object temporario in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return keyFilename;
	}
	
	/**
	 * Metodo muda um arquivo de um diretorio para outro do bucket no AWS
	 * 
	 * @param keyFilename
	 * @param folderNameOrigem
	 * @param destinoFolderName
	 * @return - nome do arquivo
	 * @throws IOException
	 */
	public String mudarArquivosDeDiretoriosStorageAmazon(String keyFilename, String folderNameOrigem, String destinoFolderName) throws IOException{
		String sourceBucketName = bucketName;
		String sourceKey = folderNameOrigem +"/"+ keyFilename;
		String destinationBucketName = bucketName;
		String destinationKey = destinoFolderName +"/"+ keyFilename;
		
		//copia arquivo de um diretorio de origem para outro diretorio de destino
		log.info("AWS - Copy object in folder to other folder trying...");
		this.amazonS3client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);		
		log.info("AWS - Copy object in folder to other folder in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + sourceKey
				+ " / destinationBucketName: " + destinationBucketName + " - destinationKey: " + destinationKey);
		
		//deleta arquivo do diretorio antigo de origem
		this.deletaArquivosDiretorioStorageAmazon(keyFilename, folderNameOrigem);
		return keyFilename;
	}
	
	/**
	 * Copiar arquivo no diretorio do bucket
	 * @param keyFilename
	 * @param folderNameOrigem
	 * @param destinoFolderName
	 * @return
	 * @throws IOException
	 */
	public String copiarArquivosNosDiretoriosStorageAmazon(String keyFilename, String folderNameOrigem, String destinoFolderName) throws IOException{
		log.info("AWS - Copy object in folder to other folder trying...");
		String sourceBucketName = bucketName;
		String sourceKey = folderNameOrigem +"/"+ keyFilename;
		String destinationBucketName = bucketName;
		
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, destinoFolderName);
		String destinationKey = destinoFolderName +"/"+ keyFilename;
		
		//copia arquivo de um diretorio de origem para outro diretorio de destino
		this.amazonS3client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);		
		log.info("AWS - Copy object in folder endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + sourceKey
				+ " / destinationBucketName: " + destinationBucketName + " - destinationKey: " + destinationKey);
		return keyFilename;
	}
	
	/**
	 * Metodo faz upload de arquivos para o bucket no AWS
	 * @param keyFilename
	 * @param file
	 * @return String - nome do arquivo salvo
	 */
	public String uploadDeArquivosParaStorageAmazon(String keyFilename, File file){
		log.info("AWS - Put object trying...");
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, null);
		this.amazonS3client.putObject(bucketName, keyFilename, file);
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
		return keyFilename;
	}
	
	/**
	 * Metodo faz upload de arquivos para um diretorio do bucket no AWS
	 * 
	 * @param keyFilename
	 * @param folderName
	 * @param file
	 * @return String - nome do arquivo salvo
	 */
	public String uploadDeArquivosParaDiretorioStorageAmazon(String keyFilename, String folderName, File file){
		log.info("AWS - Put object in folder trying...");
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, folderName);
		String pathFile = folderName +"/"+ keyFilename;
		this.amazonS3client.putObject(bucketName,  pathFile, file);
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return keyFilename;
	}
	
	/**
	 * Metodo exclui um arquivo do bucket no AWS
	 * 
	 * @param keyFilename - nome do arquivo
	 */
	public void deletaArquivosStorageAmazon(String keyFilename){
		log.info("AWS - Delete object trying...");
		this.amazonS3client.deleteObject(bucketName, keyFilename);
		log.info("AWS - Delete object success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
	}
	
	/**
	 * Metodo exclui um arquivo de um diretorio do bucket no AWS
	 * 
	 * @param keyFilename
	 * @param folderName
	 */
	public void deletaArquivosDiretorioStorageAmazon(String keyFilename, String folderName){
		log.info("AWS - Delete object in folder trying...");
		String pathFile = folderName +"/"+ keyFilename;
		this.amazonS3client.deleteObject(bucketName, pathFile);
		log.info("AWS - Delete object success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
	}
	
	/**
	 * Metodo cria um diretorio no bucket
	 * @param folderName - nome da pasta
	 */
	public void criarDiretorioNoBucket(String folderName) {
		log.info("AWS - Create folder in bucket trying...");
		String SUFFIX = "/";
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);		
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent, metadata);
		this.amazonS3client.putObject(putObjectRequest);
		log.info("AWS - Create folder success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - folder: " + folderName);
	}
	
	/**
	 * Metodo retorna InputStream de um arquivo localizado no bucket AWS
	 * 
	 * @param keyFilename - nome do arquivo
	 * @return
	 * @throws IOException
	 */
	public InputStream getObjectNoBucketAmazon(String keyFilename) throws IOException{
		log.info("AWS - getObject folder in bucket trying...");
		S3Object object = this.amazonS3client.getObject(new GetObjectRequest(bucketName, keyFilename));
		InputStream objectData = object.getObjectContent();
		objectData.close();
		log.info("AWS - getObject folder success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
		return objectData;
	}
	
	/**
	 * Metodo retorna InputStream de um arquivo localizado em um diretorio no bucket AWS
	 *  
	 * @param keyFilename - nome arquivo
	 * @param folderName - pasta
	 * @return
	 * @throws IOException
	 */
	public InputStream getObjectNoDiretorioBucketAmazon(String keyFilename, String folderName) throws IOException{
		log.info("AWS - getObject folder in bucket trying...");
		String pathFile = folderName +"/"+ keyFilename;
		S3Object object = this.amazonS3client.getObject(new GetObjectRequest(bucketName, keyFilename));
		InputStream objectData = object.getObjectContent();
		objectData.close();
		log.info("AWS - getObject folder success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return objectData;
	}
	
	/**
	 * Metodo verifica se existe um arquivo em um diretorio no bucket AWS
	 * @param keyFilename - nome arquivo
	 * @param folderName - pasta
	 * @return true se existir
	 */
	public boolean jaExisteObjetoNoDiretorioBucketAmazon(String keyFilename, String folderName){
		try {
			if(folderName == null || folderName.isEmpty()){
				this.amazonS3client.getObjectMetadata(bucketName, keyFilename);
			}else{
				this.amazonS3client.getObjectMetadata(bucketName, folderName +"/"+ keyFilename);
			}
	    } catch(AmazonServiceException e) {
	    	log.info("AWS - jaExisteObjetoNoDiretorioBucketAmazon()... false");
	        return false;
	    }
		log.info("AWS - jaExisteObjetoNoDiretorioBucketAmazon()... true");
	    return true;
	}

	/**
	 * Metodo responsavel por gerar um nome unico para um arquivo novo
	 * @param keyFilename
	 * @param folderName
	 * @return nome do arquivo gerado, ex: 1470265800107_fewcmujppclspqrbmlyqydstixxmkaqr.jpg
	 */
	private String randomizeFilenameUniqueObjectAmazon(String keyFilename, String folderName){
		int randomize = 32;
		String extension = keyFilename.substring(keyFilename.lastIndexOf(".")+1);
		//String filename = keyFilename.substring(0, keyFilename.lastIndexOf("."));
		long nomeArquivoMillis = System.currentTimeMillis();
		String novoFilename = null;
		
		String randomfilename = "";
		for (int j=0; j<randomize; j++) {
			randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
		}
		novoFilename = nomeArquivoMillis + "_" + randomfilename +"."+ extension;
		
		while (this.jaExisteObjetoNoDiretorioBucketAmazon(novoFilename, folderName)){
			randomfilename = "";
			for (int j=0; j<randomize; j++) {
				randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
			}
			novoFilename = nomeArquivoMillis + "_" + randomfilename +"."+ extension;
		}
		log.info("AWS - randomizeFilenameUniqueObjectAmazon()... only novoFilename: " + novoFilename);
		return novoFilename;
	}
	
	/**
	 * Metodo responsavel por validar a duracao de um video
	 * @param keyFilename
	 * @param folderName
	 * @return
	 * @throws VideoILiketoException
	 */
	public boolean validateDurationVideo(String keyFilename, String folderName) throws VideoILiketoException {		
		double result = 0;
		try {			
			IsoFile isoFile = new IsoFile(folderName +"/"+ keyFilename);
			MovieBox moov = isoFile.getMovieBox();
			MovieHeaderBox m = moov.getMovieHeaderBox();
			long min = (m.getDuration() / m.getTimescale() / 60);
			long sec = (m.getDuration() / m.getTimescale() % 60);
			result = (min + (sec * 0.01));
			log.info("AWS - Duration video: " + result + " - keyFilename: " + keyFilename);
			isoFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//valida duracao 2:00 dois minutos
		if(result <= 2.00){
			return true;
		}else{
			//deleta arquivo fisicamente
			try {
				this.deletaArquivosDiretorioStorageAmazon(keyFilename, folderName);
				log.warn("AWS - Delete file success - video: " + folderName +"/"+ keyFilename);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//lanca exception de erro duracao video
			throw new VideoILiketoException("Video error duration");
		}		
	}

	/**
	 * get AmazonS3Client
	 * @return
	 */
	public AmazonS3Client getAmazonS3client() {
		return amazonS3client;
	}

	/**
	 * set AmazonS3Client
	 * @param amazonS3client
	 */
	public void setAmazonS3client(AmazonS3Client amazonS3client) {
		this.amazonS3client = amazonS3client;
	}
	
}
