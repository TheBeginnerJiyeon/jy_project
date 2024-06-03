package com.multi.spring.cat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.tools.javac.launcher.Main;

@Controller
@RequestMapping("/cat") // ** 는 모든 하위 폴더 전부
public class CatClassMappingController {

	@RequestMapping("/main")
	public String Main() {

		return "redirect:/main.jsp";
	}
	
	
	
	@RequestMapping("/insert_Form")
	public void insertForm() {
		
		
	}
	
	
	
	@RequestMapping("/modify_Form")
	public void modifyForm() {
		
		
	}
	
	
	
	@RequestMapping("/delete_Form")
	public void deleteForm() {
		
		
	}
	
	
	
	@RequestMapping("/list_Form")
	public void listForm() {
		
		
	}
	
	
	
	@RequestMapping("/detail_Form")
	public void detailForm() {
		
		
	}
	
	
	
	
	

	@PostMapping("/regist")
	public void registOrder(Model model) {

		model.addAttribute("message", "class regist get");

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