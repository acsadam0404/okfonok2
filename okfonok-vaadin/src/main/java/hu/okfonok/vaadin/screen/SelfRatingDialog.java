package hu.okfonok.vaadin.screen;

import hu.okfonok.user.Rating;
import hu.okfonok.user.Skill;
import hu.okfonok.user.User;
import hu.okfonok.vaadin.Dialog;
import hu.okfonok.vaadin.security.Authentication;

import java.util.List;

import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class SelfRatingDialog extends Dialog {
	private User user;
	private VerticalLayout root;
	private Stars stars;
	private boolean noSkills;

	private Button saveButton = new Button("Most elég lesz ennyi", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			stars.saveRatings();
			closeWindow();
		}
	});

	private Button saveAndContinueButton = new Button("Jöhet még kérdés!", new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			stars.saveRatings();
			try {
				root.replaceComponent(stars, stars = new Stars(user));
			} catch (NoSkillException e) {
				noSkills = true;
				closeWindow();
			}
		}
	});


	public SelfRatingDialog() {
		try {
			user = Authentication.getUser();
			stars = new Stars(user);
			setCompositionRoot(build());
			setContent(this);
		} catch (NoSkillException e) {
			noSkills = true;
		}
	}
	
	@Override
	public void showWindow() {
		if (!noSkills) {
			super.showWindow();
		}
	}


	private Component build()  {
		root = new VerticalLayout();
		root.setMargin(true);
		root.setSpacing(true);
		root.addComponent(new Label("Skálázd tudásod és jártasságod!"));
		root.addComponent(stars);
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(saveButton);
		buttons.addComponent(saveAndContinueButton);
		buttons.setComponentAlignment(saveAndContinueButton, Alignment.MIDDLE_RIGHT);
		root.addComponent(buttons);
		return root;
	}


	private class Stars extends CustomComponent {
		private List<Skill> skills;
		private VerticalLayout starsLayout;
		private User user;
		private boolean closeAfterSave;

		public static final int SHOWED_SKILLS = 4;

		public Stars(User user) throws NoSkillException {
			this.user = user;
			List<Skill> allSkills = Rating.findSkillsNotRated(user);
			if (allSkills.isEmpty()) {
				throw new NoSkillException();
			}
			if (allSkills.size() > SHOWED_SKILLS) {
				skills = allSkills.subList(0, SHOWED_SKILLS);
			}
			else {
				closeAfterSave = true;
				skills = allSkills;
			}
			setCompositionRoot(build());
		}


		private void saveRatings() {
			for (Component comp : starsLayout) {
				RatingStars rs = (RatingStars) comp;
				Skill skill = (Skill) rs.getData();
				Rating rating = new Rating();
				rating.setRatedUser(user);
				rating.setRaterUser(user);
				rating.setSkill(skill);
				rating.setValue(rs.getValue());
				rating.save();
			}
			if (closeAfterSave) {
				closeWindow();
			}
		}


		private VerticalLayout build() {
			starsLayout = new VerticalLayout();
			for (Skill skill : skills) {
				RatingStars rs = new RatingStars();
				rs.setMaxValue(10);
				rs.setData(skill);
				rs.setCaption(skill.getQuestion());
				starsLayout.addComponent(rs);
			}
			return starsLayout;
		}
	}

	private static class NoSkillException extends Exception {

	}

}
