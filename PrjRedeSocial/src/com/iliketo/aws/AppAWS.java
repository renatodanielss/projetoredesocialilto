package com.iliketo.aws;

import java.io.File;

public class AppAWS {

	public static void main(String[] args) {
		
		System.out.println("*** START APPLICATION CONNECT AWS AMAZON STORAGE ***");
		ILiketooBucketsBusinessAWS aws = new ILiketooBucketsBusinessAWS("aws-dev");
		//App.enviarArquivosParaAWS(aws);
		//aws.criarDiretorioNoBucket("upload");
		//aws.jaExisteObjetoNoDiretorioBucketAmazon("1470264753719_yiyqfvgxdjfgygdlegvsbjwujedbsqsjjpg", "upload");
		//aws.deletaArquivosDiretorioStorageAmazon("1470264753719_yiyqfvgxdjfgygdlegvsbjwujedbsqsjjpg", "upload");
		//aws.jaExisteObjetoNoDiretorioBucketAmazon("1470264753719_yiyqfvgxdjfgygdlegvsbjwujedbsqsjjpg", "upload");
		//aws.setConfigBucketPolicy("iliketoo.aws.midia-piloto");
		//aws.setConfigBucketPolicy("iliketoo.aws.midia");
	}
	
	public static String enviarArquivosParaAWS(ILiketooBucketsBusinessAWS aws){
		File diretorioLinux = new File("/home/i_like_too/i_like_too_media/upload");
		File[] listaArquivos = diretorioLinux.listFiles();
		int contSend = 0;
		int notSend = 0;
		StringBuilder builder = new StringBuilder();
		for(File file : listaArquivos){
			if(file.isFile()){
				String filename = file.getName();
				//valida extensao arquivo
				if(filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg") || filename.toLowerCase().endsWith(".png")){
					aws.uploadDeArquivosParaDiretorioStorageAmazon(filename, "upload", file);
					builder.append("Upload success file: " + filename +" - "+ file.length() + " bytes" + "\n");
					contSend++;
				}else{
					builder.append("No send file: " + filename + "\n");
					notSend++;
				}
			}
		}
		builder.append("\n\nResultados: \nEnviados: " + contSend + "\nNao enviados: " + notSend);
		return builder.toString();
	}
	
}
