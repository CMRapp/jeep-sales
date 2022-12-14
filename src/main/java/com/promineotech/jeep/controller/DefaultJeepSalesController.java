package com.promineotech.jeep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.promineotech.jeep.entity.Image;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.service.JeepSalesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class DefaultJeepSalesController implements JeepSalesController {

	@Autowired
	private JeepSalesService jeepSalesService;
	
	@Override
	public ResponseEntity<byte[]> retreiveImage(String imageId) {
		log.debug("Retreiving image with ID={}", imageId);
		Image image = jeepSalesService.retreiveImage(imageId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", image.getMimeType().getMimeType());
		headers.add("Content-Lehgth", Integer.toBinaryString(image.getData().length));
		
		return ResponseEntity.ok().headers(headers).body(image.getData());
	}
	
	@Override
	public List<Jeep> fetchJeeps(JeepModel model, String trim) {
		log.info("Retrieving Jeeps with model={} and trim={}", model, trim);
		return jeepSalesService.fetchJeeps(model, trim);
	}

	/**
	 * 
	 */
	@Override
	public String uploadImage(MultipartFile image, Long jeepPk) {
		log.debug("image={}, jeepPk={}", image, jeepPk);
		String imageId = jeepSalesService.uploadImage(image, jeepPk);
		String json = "{\"imageId\":\"" + imageId + "\"}";
		
		return json;
	}

	

}
