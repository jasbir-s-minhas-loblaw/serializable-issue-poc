package com.sdm.hw.common.util;

import com.sdm.hw.common.dto.Language;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 */
public class MultilingualString implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final Map<Language, String> map = new HashMap<Language, String>();

    public MultilingualString()
    {
    }

    public MultilingualString put(final Language language, final String text)
    {
        this.map.put(language, text);
        return this;
    }

    public MultilingualString putAll(final MultilingualString other)
    {
        if (other != null)
        {
            this.map.putAll(other.map);
        }
        return this;
    }

    public String get(final Language language)
    {
        if (language != null)
        {
            return this.map.get(language);
        }
        else
        {
            return this.map.get(Language.ENGLISH);
        }
    }

    public String nullSafeGet(final Language language)
    {
        String result = this.get(language);
        if (result == null)
        {
            result = this.get(Language.ENGLISH);
        }
        return result;
    }

    public void forEach(final Block block)
    {
        for (Map.Entry<Language, String> entry : this.map.entrySet())
        {
            block.execute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
                + ((this.map == null) ? 0 : this.map.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        MultilingualString other = (MultilingualString) obj;
        if (this.map == null)
        {
            if (other.map != null)
            {
                return false;
            }
        }
        else if (!this.map.equals(other.map))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return this.map.toString();
    }

    public static interface Block
    {
        public void execute(Language language, String text);
    }

    public void clear()
    {
        this.map.clear();
    }

    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }

    /**
     * @return A new {@link MultilingualString} that represents the
     *         concatenation of this object's strings followed by the argument's
     *         characters.
     */
    public MultilingualString concat(final String text)
    {
        final MultilingualString result = new MultilingualString();

        this.forEach(new Block()
        {
            @Override
            public void execute(final Language language,
                final String originalText)
            {
                result.put(language, originalText == null ? text
                        : originalText + text);
            }
        });
        return result;
    }

    /**
     * @return A new {@link MultilingualString} that represents the
     *         concatenation of this object's strings followed by the argument's
     *         strings.
     */
    public MultilingualString concat(
        final MultilingualString multilingualString)
    {
        final Set<Language> languagesToConcat = new HashSet<Language>();
        this.forEach(new Block()
        {
            @Override
            public void execute(final Language language, final String text)
            {
                languagesToConcat.add(language);
            }
        });
        multilingualString.forEach(new Block()
        {
            @Override
            public void execute(final Language language, final String text)
            {
                languagesToConcat.add(language);
            }
        });

        final MultilingualString result = new MultilingualString();

        for (Language eachLanguage : languagesToConcat)
        {
            result.put(eachLanguage, this.blankIfNullGet(eachLanguage)
                    + multilingualString.blankIfNullGet(eachLanguage));
        }

        return result;
    }

    private String blankIfNullGet(final Language language)
    {
        return this.get(language) == null ? "" : this.get(language);
    }

    /**
     * apply pattern to all the language texts. pattern follows
     * {@link MessageFormat#format(String, Object...)} convention.
     * 
     * @param pattern
     * @param text
     * @return
     */
    public static MultilingualString format(final String pattern,
                                            final MultilingualString text)
    {
        final MultilingualString result = new MultilingualString();

        text.forEach(new Block()
        {

            @Override
            public void execute(final Language language, final String text)
            {
                result.put(language, MessageFormat.format(pattern, text));
            }
        });

        return result;
    }

    /**
     * concatenate language texts. join ( text(en=one, es=two) , text(en=uno,
     * es=dos) ) returns a {@link MultilingualString} of text(en=oneuno,
     * es=twodos).
     * 
     * @param texts
     * @return
     */
    public static MultilingualString join(final MultilingualString... texts)
    {
        MultilingualString result = new MultilingualString();

        for (MultilingualString eachText : texts)
        {
            result = result.concat(eachText);
        }

        return result;
    }

    /**
     * similar to {@link #join(MultilingualString...)} the joined text will be
     * separated by delimiter.
     * 
     * @param delimiter
     * @param texts
     * @return
     */
    public static MultilingualString join(final String delimiter,
                                          final MultilingualString... texts)
    {
        MultilingualString result = new MultilingualString();

        for (int i = 0; i < texts.length; i++)
        {
            MultilingualString eachText = texts[i];
            result = result.concat(eachText);
            if (i < (texts.length - 1))
            {
                result = result.concat(delimiter);
            }
        }

        return result;
    }
}
