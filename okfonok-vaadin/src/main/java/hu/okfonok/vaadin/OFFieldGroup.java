package hu.okfonok.vaadin;

import org.apache.log4j.Logger;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Field;


public class OFFieldGroup<T> extends BeanFieldGroup<T> {
	private static final Logger logger = Logger.getLogger(OFFieldGroup.class);

	private Class<T> beanTypeCopy;


	public OFFieldGroup(Class<T> beanType) {
		super(beanType);
		beanTypeCopy = beanType;
	}


	public OFFieldGroup(T bean) {
		this((Class<T>) bean.getClass());
		setItemDataSource(bean);
	}


	@Override
	public void bind(Field field, Object propertyId) {
		super.bind(field, propertyId);
		configureFieldByDeclaredAnnotations(field, propertyId);

	}


	/**
	 * A propertyn beállított annotációk alapján konfigurálja a fieldet. pl: @NotNull -> required field
	 */
	private void configureFieldByDeclaredAnnotations(final Field field, Object propertyId) {
		try {
			java.lang.reflect.Field memberField = beanTypeCopy.getDeclaredField((String) propertyId);
			if (memberField.isAnnotationPresent(javax.validation.constraints.NotNull.class)) {
				field.setRequired(true);
				field.setRequiredError("A mező kitöltése kötelező");
			}

		}
		catch (NoSuchFieldException nsfex) {
			/* valószínűleg nested property-n vagyunk */
			logger.trace("Annotáció konfiguráció hiba, valószínűleg nested property-n vagyunk", nsfex);
		}
		catch (Exception e) {
			/* elnyeljük az exceptiont */
			logger.error("Hiba annotáció konfigurálásakor", e);
		}
	}


	/**
	 * Eredeti funkció megtartva, de úgy, hogy minden nem valid fieldnek adjon egy stílust.
	 * Ehhez az kell, hogy minden fieldet végigvalidáljon, ne csak az első invalidig menjen
	 */
	@Override
	public boolean isValid() {
		boolean valid = true;

		for (Field field : getFields()) {
			try {
				field.validate();
				field.removeStyleName("invalid-value");
			}
			catch (InvalidValueException e) {
				valid = false;
				field.addStyleName("invalid-value");
			}
		}

		return valid;
	}


	public T getBean() {
		if (getItemDataSource() == null) {
			return null;
		}
		return getItemDataSource().getBean();
	}

}
