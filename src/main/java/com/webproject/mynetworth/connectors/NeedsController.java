package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webproject.mynetworth.entities.UserNeeds;

@Controller
public class NeedsController extends ControllerParent {

	// Needs page handler - default called when needs passed
	@RequestMapping(value = { "/needs" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String needsPage(HttpSession session, Principal principal, Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			model = this.userNeedsService.getAllNeedsCategories(model);
			model = this.userNeedsService.getAllUserNeeds(model, uid);
			model.addAttribute("user_need", new UserNeeds());
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "needs";
	}

	@RequestMapping(value = { "/adduserneed" }, method = { RequestMethod.GET })
	private String addUserNeed(Model model) {
		return "redirect:/needs";
	}

	@RequestMapping(value = { "/adduserneed" }, method = { RequestMethod.POST })
	private String addUserNeed(
			UserNeeds userNeed,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			userNeed = this.userNeedsService.addUserNeed(uid, userNeed);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/needs";
	}
	
	@RequestMapping(value = { "/updateuserneed" }, method = { RequestMethod.GET })
	private String updateUserNeed(Model model) {
		return "redirect:/needs";
	}
	
	@RequestMapping(value = { "/updateuserneed" }, method = { RequestMethod.POST })
	private String updateUserNeed(
			@RequestParam(value = "user_need_id") Integer user_need_id,
			@RequestParam(value = "need_name") String need_name,
			@RequestParam(value = "amount") Integer amount,
			@RequestParam(value = "need_id") Integer need_id,
			@RequestParam(value = "description") String description,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			this.userNeedsService.updateUserNeed(uid, user_need_id, need_name, amount, need_id, description);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/needs";
	}
	
	@RequestMapping(value = { "/deleteuserneed" }, method = { RequestMethod.GET })
	private String deleteUserNeed(Model model) {
		return "redirect:/needs";
	}

	@RequestMapping(value = { "/deleteuserneed" }, method = { RequestMethod.POST })
	private String deleteUserNeed(
			@RequestParam(value = "delete_user_need_id_list") Integer[] delete_user_need_id_list,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			this.userNeedsService.deleteUserNeeds(uid, delete_user_need_id_list);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/needs";
	}

}