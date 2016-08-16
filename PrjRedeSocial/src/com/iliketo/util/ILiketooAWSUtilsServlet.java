package com.iliketo.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iliketo.aws.AppAWS;
import com.iliketo.aws.ILiketooBucketsBusinessAWS;

/**
 * Servlet implementation class ILiketooAWSUtilsServlet
 */
@WebServlet("/aws/ILiketooAWSUtilsServlet/enviarArquivos")
public class ILiketooAWSUtilsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ILiketooAWSUtilsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ILiketooBucketsBusinessAWS aws = new ILiketooBucketsBusinessAWS("aws-dev");
		String html = AppAWS.enviarArquivosParaAWS(aws);
		ResponseILiketoo.respostaHtml(response, html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
