package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.BoardVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")
public class SBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SBoardController.class);

	@Autowired
	BoardService service;

	@Resource(name = "uploadPath") // bean의 id 이름
	String uploadPath;


	@ResponseBody
	@RequestMapping(value = "displayFile") // displayFile?filename={파일이름}
	public ResponseEntity<byte[]> displayFile(String filename) throws IOException {
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;

		logger.info("========================displayFile=============================");

		try {
			// IOUtils.toByteArray(in) : 해당데이터의 Byte를 꺼내주는 역할
			// 이미지 종류별로 형식이 다르다. byte 배열순이 다름...
			// BMP는 565... jpg는 다르게...
			// >>>>>>>>>> 이미지타입을 알려줘야 한다
			String formatName = filename.substring(filename.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(mType);
			in = new FileInputStream(uploadPath + "/" + filename);

			// [ header | body ] 전송되는 byte 모양...
			// header는 전송하고자 하는 정보... 어디로가니... 사이즈가 얼마다... 저장됨
			// body에는 전송할 data 원본 담겨서 감

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), header, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close(); // 닫지 않으면 메모리 누수 현상
		}

		return entity;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGet() throws Exception {
		return "sboard/register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String resgterPOST(BoardVO vo, List<MultipartFile> imgFiles) throws Exception {
		logger.info("******************************" + vo.toString());
		ArrayList<String> list = new ArrayList<>();
		logger.info("===========================빈거니?? : "+imgFiles.get(0).getOriginalFilename());
		if (!imgFiles.get(0).getOriginalFilename().equals("")) {
			// imgFiles.get(0).getSize() == 0 으로 쓰기도 함....
			logger.info("===========================빈거니?? : "+imgFiles.get(0).getOriginalFilename());
			for (MultipartFile file : imgFiles) {
				logger.info("filename : " + file.getOriginalFilename());
				String thumb = UploadFileUtils.uplaodFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				list.add(thumb); 
			}
			vo.setFiles(list.toArray(new String[list.size()]));
		}
		service.regist(vo);
		// 400이 정상~ files Mutipart...클래스로 와야함
		return "redirect:listPage";
	}

	@RequestMapping(value = "listPage", method = RequestMethod.GET)
	public String listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("=============list         Page=========");
		logger.info(cri.toString());
		model.addAttribute("list", service.listSearch(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.searchCount(cri));
		model.addAttribute("pageMaker", pageMaker);
		return "sboard/listPage";
	}

	@RequestMapping(value = "read", method = RequestMethod.GET)
	public String readPage(int bno, boolean fromlist, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {
		System.out.println("=============readPage=========");
		System.out.println(cri.toString());
		BoardVO board = service.read(bno);
		System.out.println("=============board=========    : " + board.toString());
		if (fromlist) {
			board.setViewcnt(board.getViewcnt() + 1);
			service.modify(board);
		}

		model.addAttribute("board", board);
		model.addAttribute("fromlist", true);
		return "sboard/read";
	}

	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String modifyGet(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		BoardVO board = service.read(bno);
		model.addAttribute("board", board);
		return "sboard/modify";
	}

	@Transactional
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modifyPost(BoardVO board, @ModelAttribute("cri") SearchCriteria cri,  List<MultipartFile> addFiles, 
			String[] delFiles, Model model, RedirectAttributes rttr) throws Exception {
		try{
			logger.info("boardboardboardboardboard : "+board.getBno());
			logger.info("===파일추가 파일추가  파일추가  파일추가  파일추가  파일추가  파일추가  파일추가  파일추가  파일추가  파일추가  파일추가  파일추가   ===");
			
			if(addFiles.get(0).getOriginalFilename().equals("") == false){
				logger.info("addFiles.toString() : "+addFiles.toString());
				logger.info("addFiles.size() : "+addFiles.size());
				logger.info("addFiles.get(0) : "+addFiles.get(0));
				
				for (MultipartFile file : addFiles) {
					logger.info("filename : " + file.getOriginalFilename());
					// 1. 파일 추가 처리를 한다...
					String thumb = UploadFileUtils.uplaodFile(uploadPath, file.getOriginalFilename(), file.getBytes());
					
					// 2. tbl_attach에도 추가처리를 한다...
					service.addFile(thumb, board.getBno());
				}
			}
			
			logger.info("===파일삭제  파일삭제   파일삭제   파일삭제   파일삭제   파일삭제   파일삭제   파일삭제   파일삭제   파일삭제   파일삭제   파일삭제    ===");
			
			
			if(delFiles != null){
				logger.info("delFiles.length : "+delFiles.length);
				logger.info("delFiles[0] : "+delFiles[0]);
				// 1. 파일 삭제 처리를 한다...
				
				// 2. tbl_attach에도 삭제처리를 한다...
				for(int i =0; i < delFiles.length; i++){
					deleteFile(delFiles[i]);
				}
			}
			
			service.modify(board); // tbl_board수정
			
			PageMaker pm = new PageMaker();
			pm.setCri(cri);
			// RedirectAttributes은 jsp에서 사용 불가.. Controller에서만 사용가능하다
			
			logger.info("=======================rttr.addAttribute : "+board.getBno());
			rttr.addAttribute("bno", board.getBno());
			rttr.addAttribute("page", cri.getPage());
			rttr.addAttribute("perPageNum", cri.getPerPageNum());
			rttr.addAttribute("searchType", cri.getSearchType());
			rttr.addAttribute("keyword", cri.getKeyword());
			// return
			// "redirect:read"+pm.makeSearch(cri.getPage())+"&bno="+board.getBno();
		}catch(Exception e){
		}
		
		return "redirect:read";
	}
	
	
	
	public ResponseEntity<String> deleteFile(String fullname) {
		ResponseEntity<String> entity = null;
		try {
			logger.info("DELETE FULLNAME  : " + fullname);
			logger.info("~~~~~~~~~~~~~~~~~deleteFile~~~~~~~~~~~~~  ");
			
			File file = new File(uploadPath + fullname); 
			logger.info("file~~~~~~~~~~~~~ : " +uploadPath+fullname);
			file.delete();
			
			String front = fullname.substring(0,12);
			String end = fullname.substring(14);
			String originalName = front + end;
			File file2 = new File(uploadPath+originalName);
			file2.delete();
			
			service.removeFile(fullname); // DB에서 지우기
			
			entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(BoardVO board, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		board = service.read(board.getBno());
		if(board.getFiles() != null){
			for(String filename : board.getFiles()){
				deleteFile(filename);
			}
		}
		service.remove(board.getBno());
		// model.addAttribute("bno", board.getBno());
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		return "redirect:listPage" + pm.makeSearch(cri.getPage());
	}
}
