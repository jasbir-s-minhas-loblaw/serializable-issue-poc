/*
 * All rights reserved.
 */
package com.sdm.hw.common.dto;

import com.sdm.hw.common.intf.TypeSafeEnumerable;
import com.sdm.hw.common.util.TypeSafeEnumeration;

import java.io.Serializable;
import java.util.*;

/**
 * Enumeration of available languages.
 */
public class Language implements Serializable, TypeSafeEnumerable
{

    /**
     * The Interface Block.
     */
    public interface Block
    {

        /**
         * Execute.
         * 
         * @param Language
         *            language the language
         */
        void execute(Language language);
    }

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant instances. */
    private static final TypeSafeEnumeration INSTANCES = new TypeSafeEnumeration();

    /** The Constant ENGLISH_LANGUAGE_CODE. */
    public static final String ENGLISH_LANGUAGE_CODE = "en";

    /** The Constant FRENCH_LANGUAGE_CODE. */
    public static final String FRENCH_LANGUAGE_CODE = "fr";

    /** The LANGUAGE NULL. */
    public static final Language NULL = new Language(null);

    /** The Language ENGLISH. */
    public static final Language ENGLISH = new Language(
            Language.ENGLISH_LANGUAGE_CODE);

    /** The Constant FRENCH. */
    public static final Language FRENCH = new Language(
            Language.FRENCH_LANGUAGE_CODE);

    /**
     * For each active do.
     * 
     * @param Block
     *            block the block
     */
    public static void forEachActiveDo(final Block block)
    {
        block.execute(Language.ENGLISH);
        block.execute(Language.FRENCH);
    }

    /**
     * Gets the default instance.
     * 
     * @return the default instance
     */
    public static Language getDefaultInstance()
    {
        return Language.getInstance(Locale.getDefault());
    }

    /**
     * Gets the single instance of Language.
     * 
     * @param Locale
     *            locale the locale
     * @return single instance of Language
     */
    public static Language getInstance(final Locale locale)
    {
        return (Language) Language.INSTANCES.get(locale.getLanguage());
    }

    /**
     * Gets the single instance of Language.
     * 
     * @param Serializable
     *            languageCode the language code
     * @return single instance of Language
     */
    public static Language getInstance(final Serializable languageCode)
    {
        return (Language) Language.INSTANCES.get(languageCode);
    }

    /**
     * Creates a list of all available Languages.
     * 
     * @return the list of languages
     */
    public static List<Language> getLanguages()
    {
        final Collection<TypeSafeEnumerable> values = Language.INSTANCES
                .getInstances().values();

        final List<Language> list = new ArrayList<Language>();
        for (final TypeSafeEnumerable enumerable : values)
        {
            list.add((Language) enumerable);
        }
        return list;
    }

    /**
     * Gets the locale for CANADA.
     * 
     * @param String
     *            languageCode the language code
     * @return the locale
     */
    public static Locale getLocale(final String languageCode)
    {
        Locale result = Locale.CANADA;

        if (languageCode != null
                && languageCode.equals(Language.FRENCH_LANGUAGE_CODE))
        {
            result = Locale.CANADA_FRENCH;
        }
        return result;
    }

    /**
     * Checks if Given language code is of english.
     * 
     * @param String
     *            languageCode the language code
     * @return true, if is english
     */
    public static boolean isEnglish(final String languageCode)
    {
        final String langCode = languageCode;
        final String sss = Locale.CANADA.getLanguage();
        return sss.equals(langCode);
    }

    /** The initialized. */
    private boolean initialized;

    /** The id. */
    private String languageId;

    /** The active. */
    private boolean active;

    /** The description en. */
    private String descriptionEn;

    /** The description fr. */
    private String descriptionFr;

    /** The last update datetime. */
    private Date lastUpdateDatetime;

    /** The correspondence. */
    private boolean correspondence;

    /** The monograph. */
    private boolean monograph;

    /** The counsel. */
    private boolean counsel;

    /** The application supported. */
    private boolean applicationSupported;

    /**
     * Instantiates a new language.
     */
    public Language()
    {
        /**
         * A constructor for Instantiating a new language.
         */
    }

    /**
     * Instantiates a new language.
     * 
     * @param String
     *            languageCode the language code
     */
    public Language(final String languageCode)
    {
        setLanguageCode(languageCode);

        if (languageCode != null)
        {
            Language.INSTANCES.add(this);
        }
    }
    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    @Override
    public boolean equals(final Object obj)
    {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (languageId == null) {
			if (other.languageId != null)
				return false;
		} else if (!languageId.equals(other.languageId))
			return false;
		return true;
	}

    /**
     * Gets the description based on language French or English.
     * 
     * @return the description
     */
    public String getDescription()
    {
        String result = null;
        if (isFrench())
        {
            result = descriptionFr;
        }
        else
        {
            result = descriptionEn;
        }
        return result;
    }

    /**
     * Gets the description for English.
     * 
     * @return the description en
     */
    public String getDescriptionEn()
    {
        return descriptionEn;
    }

    /**
     * Gets the description for french.
     * 
     * @return the description fr
     */
    public String getDescriptionFr()
    {
        return descriptionFr;
    }

    /**
     * The ISO code of the language.
     * 
     * @return the id
     */
    public String getId()
    {
        return languageId;
    }

    /**
     * Gets the language id.
     * 
     * @return the language id
     */
    public String getLanguageId()
    {
        return languageId;
    }

    /**
     * Gets the last update datetime.
     * 
     * @return the last update datetime
     */
    public Date getLastUpdateDatetime()
    {
        return lastUpdateDatetime;
    }

    /**
     * Gets the locale.
     * 
     * @return the locale
     */
    public Locale getLocale()
    {
        return Language.getLocale(languageId);
    }

    /**
     * Initialises Language Object.
     * 
     * @return id
     */
    @Override
    public Serializable getTypeSafeEnumerationId()
    {
        return getId();
    }
    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode()
    {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((languageId == null) ? 0 : languageId.hashCode());
		return result;
	}

    /**
     * This method initializes Language Object.
     * 
     * @param TypeSafeEnumerable
     *            other the other
     */
    @Override
    public void initialize(final TypeSafeEnumerable other)
    {
        final Language language = (Language) other;
        setActive(language.isActive());
        setDescriptionEn(language.getDescriptionEn());
        setDescriptionFr(language.getDescriptionFr());
        setLastUpdateDatetime(language.getLastUpdateDatetime());
        setCorrespondence(language.isCorrespondence());
        setMonograph(language.isMonograph());
        setCounsel(language.isCounsel());
        initialized = true;
    }

    /**
     * Checks if active.
     * 
     * @return true, if active
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * Checks if is application supported.
     * 
     * @return true, if is application supported
     */
    public boolean isApplicationSupported()
    {
        return applicationSupported;
    }

    /**
     * Checks if correspondence.
     * 
     * @return true, if correspondence
     */
    public boolean isCorrespondence()
    {
        return correspondence;
    }

    /**
     * Checks if is counsel.
     * 
     * @return true, if is counsel
     */
    public boolean isCounsel()
    {
        return counsel;
    }

    /**
     * Checks if language code is english.
     * 
     * @return true, if is english
     */
    public boolean isEnglish()
    {
        return Language.ENGLISH_LANGUAGE_CODE.equals(getId());
    }

    /**
     * Checks if language code is french.
     * 
     * @return true, if is french
     */
    public boolean isFrench()
    {
        return Language.FRENCH_LANGUAGE_CODE.equals(getId());
    }

    /**
     * Verifies if an instance is initialised or not.
     * 
     * @return boolean value
     */
    @Override
    public boolean isInitialized()
    {
        return initialized;
    }

    /**
     * Checks if is monograph.
     * 
     * @return true, if is monograph
     */
    public boolean isMonograph()
    {
        return monograph;
    }

    /**
     * this method is used for Serialization.It ensures that only singleton
     * instance is used in Serialization.
     * 
     * @return Object
     */
    @Override
    public Object readResolve()
    {
        return Language.INSTANCES.readResolve(this);
    }

    /**
     * Sets the active.
     * 
     * @param boolean active the new active
     */
    public void setActive(final boolean active)
    {
        this.active = active;
    }

    /**
     * Sets the application supported.
     * 
     * @param boolean applicationSupported the new application supported
     */
    public void setApplicationSupported(final boolean applicationSupported)
    {
        this.applicationSupported = applicationSupported;
    }

    /**
     * Sets the correspondence.
     * 
     * @param boolean correspondence the new correspondence
     */
    public void setCorrespondence(final boolean correspondence)
    {
        this.correspondence = correspondence;
    }

    /**
     * Sets the counsel.
     * 
     * @param boolean counsel the new counsel
     */
    public void setCounsel(final boolean counsel)
    {
        this.counsel = counsel;
    }

    /**
     * Sets the description for English.
     * 
     * @param String
     *            descriptionEn the new description en
     */
    public void setDescriptionEn(final String descriptionEn)
    {
        this.descriptionEn = descriptionEn;
    }

    /**
     * Sets the description for french.
     * 
     * @param String
     *            descriptionFr the new description fr
     */
    public void setDescriptionFr(final String descriptionFr)
    {
        this.descriptionFr = descriptionFr;
    }

    /**
     * Sets the initialized.
     * 
     * @param boolean initialized the new initialized
     */
    public void setInitialized(final boolean initialized)
    {
        this.initialized = initialized;
    }

    /**
     * Sets the id.
     * 
     * @param String
     *            langageCode the new language code
     */
    public void setLanguageCode(final String langageCode)
    {
        languageId = langageCode;
    }

    /**
     * Sets the language id.
     * 
     * @param String
     *            languageId the new language id
     */
    public void setLanguageId(final String languageId)
    {
        this.languageId = languageId;
    }

    /**
     * Sets the last update datetime.
     * 
     * @param Date
     *            lastUpdateDatetime the new last update datetime
     */
    public void setLastUpdateDatetime(final Date lastUpdateDatetime)
    {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    /**
     * Sets the monograph.
     * 
     * @param boolean monograph the new monograph
     */
    public void setMonograph(final boolean monograph)
    {
        this.monograph = monograph;
    }

    /**
     * Converting into String .
     * 
     * @return String id the id
     */
    @Override
    public String toString()
    {
        return getId();
    }

}
