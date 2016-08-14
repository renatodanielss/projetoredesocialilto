package com.iliketo.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import HardCore.DB;

import com.iliketo.dao.EventDAO;
import com.iliketo.dao.MemberDAO;
import com.iliketo.exception.ImageILiketoException;
import com.iliketo.exception.StorageILiketoException;
import com.iliketo.model.Event;
import com.iliketo.model.Member;
import com.iliketo.service.NotificationService;
import com.iliketo.util.CmsConfigILiketo;
import com.iliketo.util.LogUtilsILiketoo;
import com.iliketo.util.ModelILiketo;
import com.iliketo.util.Str;


@Controller
public class PerfilMembroController {
	
	private @Autowired HttpServletRequest request;
	static final Logger log = Logger.getLogger(PerfilMembroController.class);
	
	/**
	 * Método intercepta erros de Exception, salva no log e direciona para pagina de erro.
	 */
	@ExceptionHandler(Exception.class)
	public void errorResponse(Exception ex, HttpServletRequest req, HttpServletResponse res){
		String pageError = "/page.jsp?id=902";
		LogUtilsILiketoo.mostrarLogStackException(ex, log, req, res, pageError);
	}
	
	/**
	 * Redireciona pagina para visualizar meu perfil ou terceiro
	 */
	@RequestMapping(value={"/profile/{abaTela}/{idMember}"}, method=RequestMethod.GET)
	public String perfilMembro(@PathVariable String abaTela, @PathVariable String idMember,
			HttpServletResponse response){
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		ModelILiketo model = new ModelILiketo(request, response);
		
		if(ModelILiketo.validateAndProcessError(request)){
			//valida e mostra error na pagina
			log.warn("Erro ao criar adicionar uma foto de perfil ou capa do membro.");
		}
		
		//visualizar abas do perfil (colecao, anuncio, evento e hobby)
		if(abaTela.equals("memberCollections") || abaTela.equals("memberAds")
				|| abaTela.equals("memberEvents") || abaTela.equals("memberHobbies")){
			model.addAttribute("abaTela", abaTela);
			Member membro = (Member) request.getSession().getAttribute("member");			
			if(membro.getIdMember().equals(idMember)){
				//meu perfil
				model.addAttribute("membroPerfil", membro);			
				return "/page.jsp?id=1117";
			}else{
				//profile terceiro
				MemberDAO dao = new MemberDAO(db, request);
				Member terceiro = (Member)dao.readByColumn("id_member", idMember, Member.class);
				if(terceiro != null){
					model.addAttribute("membroPerfil", terceiro);
					return "/page.jsp?id=1118";
				}
			}
		}
		return "page.jsp?id=invalidPage";	//pagina invalida, nao achou membro ou pagina perfil
	}
	
	
	/**
	 * Adiciona ou altera a foto do perfil
	 */
	@RequestMapping(value={"/profile/member/addPhoto"})
	public String adicionarFotoMembro(HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		MemberDAO membroDAO = new MemberDAO(db, request);
		Member membro = new Member();
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImage(membro);
		} catch (StorageILiketoException e) {
			model.addAttribute("membroPerfil", membro);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/profile/memberCollections/" + membro.getIdMember());			//page perfil
		} catch (ImageILiketoException e) {
			model.addAttribute("membroPerfil", membro);
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 						//msg erro
			return model.redirectError("/ilt/profile/memberCollections/" + membro.getIdMember());			//page perfil
		}
		//seta no membro original a foto da capa
		Member original = (Member) request.getSession().getAttribute("member");
		membro.setFotoDeCapa(null);
		membro.setId(original.getId());
		membro.setIdMember(original.getIdMember());
		membroDAO.update(membro, true);
		
		original.setPathPhoto(membro.getPathPhoto());
		model.addAttribute("membroPerfil", original);
		request.getSession().setAttribute("member", original);
		return "redirect:/ilt/profile/memberCollections/" + original.getIdMember();
	}
	
	/**
	 * Adiciona ou altera a foto da capa
	 */
	@RequestMapping(value={"/profile/member/addCapa"})
	public String adicionarFotoCapa(HttpServletResponse response) throws Exception{
		
		log.info(request.getRequestURL());
		DB db = (DB) request.getAttribute(Str.CONNECTION_DB);
		CmsConfigILiketo cms = new CmsConfigILiketo(request, response);
		MemberDAO membroDAO = new MemberDAO(db, request);
		Member membro = new Member();
		
		ModelILiketo model = new ModelILiketo(request, response);
		try {
			cms.processFileuploadImage(membro);
		} catch (StorageILiketoException e) {
			model.addAttribute("membroPerfil", membro);
			model.addMessageError("freeSpace", "You do not have enough free space, needed " +cms.getSizeFilesInBytes()/1024+ " KB.");	//msg erro
			return model.redirectError("/ilt/profile/memberCollections/" + membro.getIdMember());			//page perfil
		} catch (ImageILiketoException e) {
			model.addAttribute("membroPerfil", membro);
			model.addMessageError("imageFormat", "Upload only Image in jpg format."); 						//msg erro
			return model.redirectError("/ilt/profile/memberCollections/" + membro.getIdMember());			//page perfil
		}
		//seta no membro original a foto da capa
		Member original = (Member) request.getSession().getAttribute("member");
		membro.setPathPhoto(null);
		membro.setId(original.getId());
		membro.setIdMember(original.getIdMember());
		membroDAO.update(membro, true);
		
		original.setFotoDeCapa(membro.getFotoDeCapa());
		model.addAttribute("membroPerfil", original);
		request.getSession().setAttribute("member", original);
		return "redirect:/ilt/profile/memberCollections/" + original.getIdMember();
	}

}
