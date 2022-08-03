package com.webproject.mynetworth.connectors;

import java.security.Principal;
import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.webproject.mynetworth.entities.UserIncome;
import com.webproject.mynetworth.services.SaveImageFileToStorage;



@Controller
public class MyProfileController extends ControllerParent {

	// MyProfile page handler - default called when myprofile passed
	@RequestMapping(value = { "/myprofile" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String myProfilePage(HttpSession session, Principal principal, Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			model = this.getRequiredUserDetails.myProfilePageDetails(model, uid);
			model = this.userIncomeService.getAllIncomeSources(model, uid);
			model.addAttribute("user_income", new UserIncome());
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "myprofile";
	}

	@RequestMapping(value = { "/update_profile" }, method = { RequestMethod.GET })
	private String updateProfileGet(Model model) {
		return "redirect:/myprofile";
	}

	@RequestMapping(value = { "/update_profile" }, method = { RequestMethod.POST })
	private String updateProfilePost(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "dob") Date dob,
			@RequestParam(value = "about_me") String about_me,
			@RequestParam(value = "image_url") MultipartFile profile_image_file,
			@RequestParam(value = "cover_image_url") MultipartFile cover_image_file,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);

		try {
			SaveImageFileToStorage.saveImageToStorge("profile", profile_image_file);
			SaveImageFileToStorage.saveImageToStorge("cover", cover_image_file);
			String profile_image_name = StringUtils.cleanPath(profile_image_file.getOriginalFilename());
			String cover_image_name = StringUtils.cleanPath(cover_image_file.getOriginalFilename());
			this.setRequiredUserDetails.setUserPersonalDetails(uid, name, dob, about_me, profile_image_name,
					cover_image_name);
		} catch (Exception e) {
			// System.out.println(e);
		}

		return "redirect:/myprofile";
	}
	
	@RequestMapping(value = { "/addincomesource" }, method = { RequestMethod.GET })
	private String addIncomeSourceGet(Model model) {
		return "redirect:/myprofile";
	}

	@RequestMapping(value = { "/addincomesource" }, method = { RequestMethod.POST })
	private String addIncomeSource(
			UserIncome userIncome,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			userIncome = this.userIncomeService.addIncomeSource(uid, userIncome);
			// System.out.println(userIncome);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/myprofile";
	}
	
	@RequestMapping(value = { "/updateincomesource" }, method = { RequestMethod.GET })
	private String updateIncomeSourceGet(Model model) {
		return "redirect:/myprofile";
	}

	@RequestMapping(value = { "/updateincomesource" }, method = { RequestMethod.POST })
	private String updateIncomeSource(
			@RequestParam(value = "income_id") Integer income_id,
			@RequestParam(value = "source_name") String source_name,
			@RequestParam(value = "amount") Integer amount,
			@RequestParam(value = "description") String description,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			this.userIncomeService.updateIncomeSource(uid, income_id, source_name, amount, description);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/myprofile";
	}
	
	@RequestMapping(value = { "/deleteincomesource" }, method = { RequestMethod.GET })
	private String deleteIncomeSourceGet(Model model) {
		return "redirect:/myprofile";
	}

	@RequestMapping(value = { "/deleteincomesource" }, method = { RequestMethod.POST })
	private String deleteIncomeSource(
			@RequestParam(value = "income_id_list") Integer[] income_id_list,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			this.userIncomeService.deleteIncomeSources(uid, income_id_list);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/myprofile";
	}

}