package com.multi.spring.cat.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.multi.spring.cat.model.dto.CatDTO;
import com.multi.spring.cat.service.CatService;
import com.multi.spring.page.model.dto.PageDTO;
import com.multi.spring.users.model.dto.UsersDTO;

@Controller
@RequestMapping("/cat") // ** 는 모든 하위 폴더 전부
public class CatController {

	private final CatService catService;

	public CatController(CatService catService) {
		super();
		this.catService = catService;
	}

	@RequestMapping("/main")
	public String Main() {

		return "redirect:/";
	}

	@RequestMapping("/catMain")
	public void catMain() {

	}
	
	@RequestMapping("/insert_form")
	public void insertForm() {
		
	}

	@RequestMapping("/modify_form")
	public void modifyForm() {

	}

	@RequestMapping("/delete")
	public void delete() {

	}

	// 나중에 등록한 이미지가 보이게 넣기
	@GetMapping("/list")
	public void list(@RequestParam("page") int page, Model model) {
		
		PageDTO pageDTO=new PageDTO();
		
		pageDTO.setPage(page);

		System.out.println("page: " + pageDTO.getPage());

		pageDTO.setStartEnd(pageDTO.getPage());

		try {
			List<CatDTO> list = catService.selectList(pageDTO);

			System.out.println("list: "+list);
			
			int count = catService.selectCount();

			int pages = count / 5 + 1;
			
			model.addAttribute("list",list);
			model.addAttribute("count",count);
			model.addAttribute("pages",pages);
			
			System.out.println("pages : " + pages);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@GetMapping("/catList2")
	public void list2(PageDTO pageDTO, Model model) {
		
		
		
		System.out.println("page: list2" + pageDTO.getPage());
		
		pageDTO.setStartEnd(pageDTO.getPage());
		
		try {
			List<CatDTO> list = catService.selectList(pageDTO);
			
			System.out.println("list: "+list);
			
			
			model.addAttribute("list",list);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@RequestMapping("/detail_form")
	public void detailForm() {

	}

	// 등록할 때도 썸네일 넣기 도전??  
	@PostMapping("/insert")
	public void insertCat(HttpServletRequest httpServletRequest, HttpSession httpSession, Model model, CatDTO catDTO,
			MultipartFile singleFile) {

		// 파일을 서버에 저장

		// 경로 지정, 폴더 생성
		String root = httpServletRequest.getSession().getServletContext().getRealPath("resources");

		System.out.println("root: " + root);

		String filePath = root + "\\uploadFiles";

		File mkdir = new File(filePath);

		if (!mkdir.exists()) {
			mkdir.mkdirs();
		}

		System.out.println("singleFile" + singleFile);

		// 파일 명 변경
		String originalFileName = singleFile.getOriginalFilename();
		String ext = originalFileName.substring(originalFileName.lastIndexOf("."));

		System.out.println("originalFileName" + originalFileName);
		System.out.println("ext" + ext);

		String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

		// 파일을 서버에 저장

		try {
			singleFile.transferTo(new File(filePath + "\\" + savedName));

			model.addAttribute("savedName", savedName);

			catDTO.setImg(savedName);

			// 그 외 유저의 아이디를 보낼 캣dto에 저장
			UsersDTO loginDto = (UsersDTO) httpSession.getAttribute("loginUser");

			String userId = loginDto.getId();

			catDTO.setCreatedPerson(userId);

			int result = catService.insertCat(catDTO);

			model.addAttribute("catDTO", catDTO);

			System.out.println("catDTO : " + catDTO);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

			new File(filePath + "\\" + savedName).delete();
			model.addAttribute("message", "파일 업로드 실패!!");

		}

		model.addAttribute("message", "cat insert succeess!!");

	}

	@RequestMapping(value = { "modify", "delete" }, method = RequestMethod.POST)
	public String modifyAndDelete(Model model) {

		model.addAttribute("message", "POST 방식의 고양이 정보 수정과 주문 정보 삭제 공통 처리용 핸들러 메소드 호출함...");

		return "mappingResult";
	}

	@GetMapping("detail/{orderNo}")
	public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo) {
		model.addAttribute("message", orderNo + "번 고양이 상세 내용 조회용 핸들러 메소드 호출함...");

		return "mappingResult";
	}

}

//@RestController :  RestController 어노테이션을 사용하면 @ResponseBody 를 일일이 선언 안해도 되게 지원해준다. 
//restController 차체가 controller  + @ResponseBody  ->ajax수업때 진행, springboot보강시
