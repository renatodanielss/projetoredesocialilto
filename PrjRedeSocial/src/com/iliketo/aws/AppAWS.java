package com.iliketo.aws;

import java.io.File;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AppAWS {
	
	static final Logger log = Logger.getLogger(AppAWS.class);

	public static void main(String[] args) {
		
		System.out.println("*** START APPLICATION CONNECT AWS AMAZON STORAGE ***");
		ILiketooBucketsBusinessAWS aws = new ILiketooBucketsBusinessAWS(ILiketooBucketsBusinessAWS.AWS_DEV);
		//App.enviarArquivosParaAWS(aws);
		//ObjectListing lista = aws.getAmazonS3client().listObjects("iliketoo.aws.midia-piloto");
		//for (S3ObjectSummary objectSummary : lista.getObjectSummaries()) {
			//System.out.println("key: " + objectSummary.getKey());
	        //aws.getAmazonS3client().deleteObject("iliketoo.aws.midia-piloto", objectSummary.getKey());
	    //}
		//aws.criarDiretorioNoBucket("temp");
		//aws.jaExisteObjetoNoDiretorioBucketAmazon("1470264753719_yiyqfvgxdjfgygdlegvsbjwujedbsqsjjpg", "upload");
		//aws.deletaArquivosDiretorioStorageAmazon("1470264753719_yiyqfvgxdjfgygdlegvsbjwujedbsqsjjpg", "upload");
		//aws.jaExisteObjetoNoDiretorioBucketAmazon("1470264753719_yiyqfvgxdjfgygdlegvsbjwujedbsqsjjpg", "upload");
		//aws.setConfigBucketPolicy("iliketoo.aws.midia-piloto");
		//aws.setConfigBucketPolicy("iliketoo.aws.midia");
	}
	
	public static String enviarArquivosParaAWS(ILiketooBucketsBusinessAWS aws){
		File diretorioLinux = new File("/home/i_like_too/i_like_too_media/upload"); //home/i_like_too/i_like_too_media/upload
		File[] listaArquivos = diretorioLinux.listFiles();
		int contSend = 0;
		int notSend = 0;
		long inicio = System.currentTimeMillis();
		long tamanhoTotal = 0;
		StringBuilder builder = new StringBuilder();
		log.info("***UPLOAD DE ARQUIVOS PARA AMAZON*** Total: " + listaArquivos.length);
		for(File file : listaArquivos){
			if(file.isFile()){
				String filename = file.getName();
				long antes = System.currentTimeMillis();
				//String newname = aws.uploadDeArquivosParaDiretorioStorageAmazon(filename, "upload", file);
				aws.getAmazonS3client().putObject("iliketoo.aws.midia-piloto", "upload/" + filename, file);
				long depois = System.currentTimeMillis();
				contSend++;
				log.info("CONT: "+contSend + " - Upload success file: " + filename +" - "+ file.length() + " bytes - tempo: " +((depois-antes) <= 0 ? "0 s" : (depois-antes)/1000L+" s"));
				builder.append("Upload success file: " + filename +" - "+ file.length() + " bytes - tempo: " +((depois-antes) <= 0 ? "0 s" : (depois-antes)/1000L+" s<br>"));
				tamanhoTotal += file.length()/1024;
			}
		}
		long fim = System.currentTimeMillis();
		log.info("\n\nResultados: \nEnviados: " + contSend + "\nNao enviados: " + notSend + "\nTempo total: " + (fim - inicio)/1000L
				+ "\nSize MB: " + tamanhoTotal/1024);
		builder.append("<br><br>Resultados: <br>Enviados: " + contSend + "<br>Nao enviados: " + notSend + "<br>Tempo total: " + (fim - inicio)/1000L
				+ "<br>Size MB: " + tamanhoTotal/1024);
		return builder.toString();
	}
	
}
