package kh.com.kshrd.restaurant.restcontrollers;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/api/admin/upload")
public class RestUploadController {

	@RequestMapping(value="/single", method = RequestMethod.POST)
	@ApiOperation("TO UPLOAD A SINGLE FILE.")
	public ResponseEntity<Map<String, Object>> uploadSingle(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request){
		System.out.println("FILE ==> "+file.getOriginalFilename());
		String filename = "";
        Map<String, Object> map = new HashMap<String, Object>();
		if (!file.isEmpty()) {
			String savePath= request.getSession().getServletContext().getRealPath("/resources/images");
            try { 
            	filename = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                UUID uuid = UUID.randomUUID();
                String randomUUIDFileName = uuid.toString();
                String extension = filename.substring(filename.lastIndexOf(".")+1);
				System.out.println(savePath);
				File path = new File(savePath);
				if(!path.exists()){
					path.mkdirs();
				} 
				filename = randomUUIDFileName + "." + extension;
                BufferedOutputStream stream = new BufferedOutputStream(
                									new FileOutputStream(
                											new File(savePath + File.separator  + filename)));
                stream.write(bytes);
                stream.close();
                
/*                java.awt.Image img = null;
                BufferedImage tempJPG = null;
                File newFileJPG = null;
                
                img = ImageIO.read(new File(savePath + File.separator  + filename));
                tempJPG = resizeImage(img, 100, 100);
                newFileJPG = new File(savePath + File.separator + randomUUIDFileName+"_thumb."+ extension);
                ImageIO.write(tempJPG, "jpg", newFileJPG);*/
                
                File destinationDir = new File(savePath + File.separator+"thumbnails");
                destinationDir.mkdirs();
                /*Thumbnails.of(filename)
                	.size(200, 200)
                	.toFiles(destinationDir, Rename.PREFIX_DOT_THUMBNAIL);*/
                
                ConvertCmd cmd = new ConvertCmd();
                String destinationFileName =savePath;
                File thumbNailFile = new File(savePath + File.separator+"thumbnails"+File.separator+filename);
                if (!thumbNailFile.exists()) {
	                IMOperation op = new IMOperation();
	                op.addImage(filename);
	                op.thumbnail(2000);
	                op.addImage(destinationFileName);
	                cmd.run(op);
                }

                
                System.out.println("MESSAGE ==> YOU HAVE BEEN UPLOADED " + savePath + File.separator + filename + " SUCCESSFULLY!");
        		map.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY");
        		map.put("CODE", "0000");
        		map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + filename);
        		map.put("IMAGE_NAME", filename);
        		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
            } catch (Exception e) {
            	e.printStackTrace();
                System.out.println("MESSAGE ==> YOU FAILED TO UPLOAD " + filename + " => " + e.getMessage());
                map.put("MESSAGE", "ERROR " + e.getMessage());
        		map.put("CODE", "8888");
        		map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + filename);
        		map.put("IMAGE_NAME", filename);
        		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            System.out.println("YOU FAILED TO UPLOAD " + filename + " BECAUSE THE FILE WAS EMPTY.");
    		map.put("MESSAGE", "UNSUCCESSFULLY");
    		map.put("CODE", "9999");
    		map.put("DATA", filename);
    		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
        }
	}

	@RequestMapping(value = "/multiple", method = RequestMethod.POST)
	@ApiOperation("TO UPLOAD A MULTIPLE FILES.")
	public ResponseEntity<Map<String, Object>> uploadMultiple(@RequestParam("files") CommonsMultipartFile[] files,
			@RequestParam("name") String restaurantName, HttpServletRequest request) {

		System.out.println("RESTAURANT NAME ======> " + restaurantName);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> mapFiles = new ArrayList<Map<String, Object>>();
			if (files.length < 1) {
				responseMap.put("MESSAGE", "FILE IS NOT FOUND...");
				responseMap.put("CODE", "8888");
				return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.NOT_FOUND);
			}
			for (CommonsMultipartFile file : files) {
				String filename = "";
				if (!file.isEmpty()) {
					String savePath = request.getSession().getServletContext().getRealPath("/resources/images");
					Map<String, Object> map = new HashMap<String, Object>();
					try {
						filename = file.getOriginalFilename();
						byte[] bytes = file.getBytes();
						UUID uuid = UUID.randomUUID();
						String randomUUIDFileName = uuid.toString();
						String extension = filename.substring(filename.lastIndexOf(".") + 1);
						System.out.println(savePath);
						File path = new File(savePath);
						if (!path.exists()) {
							path.mkdirs();
						}
						filename = randomUUIDFileName + "." + extension;
						BufferedOutputStream stream = new BufferedOutputStream(
								new FileOutputStream(new File(savePath + File.separator + filename)));
						stream.write(bytes);
						stream.close();

						System.out.println("MESSAGE ==> YOU HAVE BEEN UPLOADED " + savePath + File.separator + filename
								+ " SUCCESSFULLY!");
						map.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY");
						map.put("CODE", "0000");
						map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + filename);
						map.put("IMAGE_NAME", filename);
						mapFiles.add(map);
					} catch (Exception e) {
						System.out.println("MESSAGE ==> YOU FAILED TO UPLOAD " + filename + " => " + e.getMessage());
						map.put("MESSAGE", "ERROR " + e.getMessage());
						map.put("CODE", "7777");
						map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + filename);
						map.put("IMAGE_NAME", filename);
						mapFiles.add(map);
					}
				} else {
					System.out.println("YOU FAILED TO UPLOAD " + filename + " BECAUSE THE FILE WAS EMPTY.");
					responseMap.put("MESSAGE", "UNSUCCESSFULLY");
					responseMap.put("CODE", "8888");
					responseMap.put("DATA", filename);
					// return new ResponseEntity<Map<String,
					// Object>>(responseMap, HttpStatus.NOT_FOUND);
				}
			}
			responseMap.put("IMAGES", mapFiles);
			responseMap.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY.");
			responseMap.put("CODE", "0000");
		} catch (Exception ex) {
			responseMap.put("MESSAGE", "ERROR " + ex.getMessage());
			responseMap.put("CODE", "9999");
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
	}

	public static String getURLWithContextPath(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	@RequestMapping(value = "/base64single", method = RequestMethod.POST)
	@ApiOperation("TO UPLOAD A SINGLE FILE WITH BASE64.")
	public @ResponseBody ResponseEntity<Map<String, Object>> uploadImage2(@RequestBody String strBase64,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// This will decode the String which is encoded by using Base64
			// class
			byte[] imageByte = Base64.decodeBase64(strBase64);

			String savePath = request.getSession().getServletContext().getRealPath("/resources/images");

			UUID uuid = UUID.randomUUID();
			String randomUUIDFileName = uuid.toString();

			String directory = request.getServletContext().getRealPath("/resources/images/") + randomUUIDFileName
					+ ".jpg";

			new FileOutputStream(directory).write(imageByte);

			map.put("MESSAGE", "YOU HAVE BEEN UPLOADED SUCCESSFULLY");
			map.put("CODE", "0000");
			map.put("IMAGE_URL", getURLWithContextPath(request) + "/resources/images/" + randomUUIDFileName + ".jpg");
			map.put("IMAGE_NAME", randomUUIDFileName + ".jpg");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			map.put("MESSAGE", "YOU HAVE BEEN FAILED WHEN UPLOAD THE IMAGE");
			map.put("CODE", "9999");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}

	}

	public static BufferedImage resizeImage(final java.awt.Image image, int width, int height) {
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		// below three lines are for RenderingHints for better image quality at
		// cost of higher processing time
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}

}