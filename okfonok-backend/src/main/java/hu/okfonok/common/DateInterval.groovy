package hu.okfonok.common;

import hu.okfonok.BaseEntity;

import javax.persistence.Entity
import javax.persistence.Table

/* TODO ezt nem entitásként kéne ábrázolni */
@Entity
@Table(name = "dateinterval")
class DateInterval extends BaseEntity{
	private Date start;
	private Date end;

	DateInterval(Date start, Date end) {
		this.end = end;
		this.start = start;
	}
	
	Date getStart() {
		start
	}
	
	Date getEnd() {
		end
	}

	String toString(String format) {
		start.format(format) + "-" + end.format(format)
	}

	static DateInterval parse(String format, String value) {
		if (!value.contains("-")) {
			throw new IllegalArgumentException()
		}
		if (value) {
			def (first, second) = value.split("-")
			Date start = Date.parse(format, first)
			Date end = Date.parse(format, second)
			return new DateInterval(start, end)
		}
		return null
	}
}
