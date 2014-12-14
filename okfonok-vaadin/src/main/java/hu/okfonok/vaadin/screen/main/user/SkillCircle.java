package hu.okfonok.vaadin.screen.main.user;

import hu.okfonok.user.Skill;
import hu.okfonok.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PointPlacement;
import com.vaadin.addon.charts.model.TickmarkPlacement;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public class SkillCircle extends CustomComponent {
	private Chart chart;

	public SkillCircle(User user) {
		Data data = calculateData(user);
		setCompositionRoot(getChart(data));

	}

	private Data calculateData(User user) {
		Data data = new Data();
		List<Skill> skills = Skill.findAll();
		for (Skill skill : skills) {
			String skillName = skill.getName();
			Double ownRating = user.getOwnRatingForSkill(skill);
			Double averageRating = user.getAverageRatingForSkill(skill);
			data.add(skillName, ownRating, averageRating);
		}
		return data;
	}

	public Component getChart(Data data) {
		chart = new Chart(ChartType.LINE);

		Configuration conf = chart.getConfiguration();
		conf.getChart().setPolar(true);
		conf.setTitle((String) null);
		conf.setCredits(new Credits(false));
		Legend legend = new Legend();
		legend.setEnabled(false);
		conf.setLegend(legend);
		conf.getTooltip().setShared(true);
		
		XAxis axis = new XAxis();
		axis.setCategories(data.getSkills());
		axis.setTickmarkPlacement(TickmarkPlacement.ON);
		conf.addxAxis(axis);

		ListSeries ownRating = new ListSeries(data.getOwnRatings());
		ownRating.setName("Saját értékelés");
		ListSeries averageRating = new ListSeries(data.getAverageRatings());
		averageRating.setName("Mások értékelése");
		PlotOptionsLine plotOptions = new PlotOptionsLine();
		plotOptions.setPointPlacement(PointPlacement.ON);
		ownRating.setPlotOptions(plotOptions);
		averageRating.setPlotOptions(plotOptions);

		conf.setSeries(ownRating, averageRating);

		chart.drawChart(conf);

		return chart;
	}

	private static class Data {
		private List<InnerData> list = new ArrayList<>();

		public void add(String skillName, Double ownRating, Double averageRating) {
			list.add(new InnerData(skillName, ownRating, averageRating));
		}
		
		public String[] getSkills() {
			List<String> skills = new ArrayList<>();
			for (InnerData id : list) {
				skills.add(id.skillName);
			}
			return skills.toArray(new String[0]);
		}
		
		public Double[] getOwnRatings() {
			List<Double> ratings = new ArrayList<>();
			for (InnerData id : list) {
				ratings.add(id.ownRating);
			}
			return ratings.toArray(new Double[0]);
		}
		
		public Double[] getAverageRatings() {
			List<Double> ratings = new ArrayList<>();
			for (InnerData id : list) {
				ratings.add(id.averageRating);
			}
			return ratings.toArray(new Double[0]);
		}

		private static class InnerData {
			String skillName;
			Double ownRating;
			Double averageRating;

			public InnerData(String skillName, Double ownRating, Double averageRating) {
				this.skillName = skillName;
				this.ownRating = ownRating;
				this.averageRating = averageRating;
			}

		}

	}
}
