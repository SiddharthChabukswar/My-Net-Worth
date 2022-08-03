package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webproject.mynetworth.entities.UserInvestments;

@Controller
public class InvestmentsController extends ControllerParent {

	// Investments page handler - default called when investments passed
	@RequestMapping(value = { "/investments" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String investmentsPage(HttpSession session, Principal principal, Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			model = this.userInvestmentsService.getAllInvestmentsCategories(model);
			model = this.userInvestmentsService.getAllUserInvestments(model, uid);
			model.addAttribute("user_investment", new UserInvestments());
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "investments";
	}
	
	@RequestMapping(value = { "/adduserinvestment" }, method = { RequestMethod.GET })
	private String addUserInvestment(Model model) {
		return "redirect:/investments";
	}

	@RequestMapping(value = { "/adduserinvestment" }, method = { RequestMethod.POST })
	private String addUserInvestment(
			UserInvestments userInvestment,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			userInvestment = this.userInvestmentsService.addUserInvestment(uid, userInvestment);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/investments";
	}
	
	@RequestMapping(value = { "/updateuserinvestment" }, method = { RequestMethod.GET })
	private String updateUserInvestment(Model model) {
		return "redirect:/investments";
	}
	
	@RequestMapping(value = { "/updateuserinvestment" }, method = { RequestMethod.POST })
	private String updateUserInvestment(
			@RequestParam(value = "user_investment_id") Integer user_investment_id,
			@RequestParam(value = "investment_name") String investment_name,
			@RequestParam(value = "amount") Integer amount,
			@RequestParam(value = "investment_id") Integer investment_id,
			@RequestParam(value = "description") String description,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			this.userInvestmentsService.updateUserInvestment(uid, user_investment_id, investment_name, amount, investment_id, description);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/investments";
	}
	
	@RequestMapping(value = { "/deleteuserinvestment" }, method = { RequestMethod.GET })
	private String deleteUserInvestment(Model model) {
		return "redirect:/investments";
	}

	@RequestMapping(value = { "/deleteuserinvestment" }, method = { RequestMethod.POST })
	private String deleteUserInvestment(
			@RequestParam(value = "delete_user_investment_id_list") Integer[] delete_user_investment_id_list,
			HttpSession session,
			Principal principal,
			Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			this.userInvestmentsService.deleteUserInvestments(uid, delete_user_investment_id_list);
		} catch (Exception e) {
			// System.out.println(e);
		}
		return "redirect:/investments";
	}

}