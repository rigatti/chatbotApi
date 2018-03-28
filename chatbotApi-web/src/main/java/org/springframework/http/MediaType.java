//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.http;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.MimeType.SpecificityComparator;

public class MediaType extends MimeType implements Serializable {
    private static final long serialVersionUID = 2069937152339670231L;
    public static final MediaType ALL = valueOf("*/*");
    public static final String ALL_VALUE = "*/*";
    public static final MediaType APPLICATION_ATOM_XML = valueOf("application/atom+xml");
    public static final String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";
    public static final MediaType APPLICATION_FORM_URLENCODED = valueOf("application/x-www-form-urlencoded");
    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";
    public static final MediaType APPLICATION_JSON = valueOf("application/json");
    public static final String APPLICATION_JSON_VALUE = "application/json";
    public static final MediaType APPLICATION_JSON_UTF8 = valueOf("application/json; charset=utf-8");
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json; charset=utf-8";
    public static final MediaType APPLICATION_OCTET_STREAM = valueOf("application/octet-stream");
    public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
    public static final MediaType APPLICATION_PDF = valueOf("application/pdf");
    public static final String APPLICATION_PDF_VALUE = "application/pdf";
    public static final MediaType APPLICATION_PROBLEM_JSON = valueOf("application/problem+json");
    public static final String APPLICATION_PROBLEM_JSON_VALUE = "application/problem+json";
    public static final MediaType APPLICATION_PROBLEM_JSON_UTF8 = valueOf("application/problem+json;charset=UTF-8");
    public static final String APPLICATION_PROBLEM_JSON_UTF8_VALUE = "application/problem+json;charset=UTF-8";
    public static final MediaType APPLICATION_PROBLEM_XML = valueOf("application/problem+xml");
    public static final String APPLICATION_PROBLEM_XML_VALUE = "application/problem+xml";
    public static final MediaType APPLICATION_RSS_XML = valueOf("application/rss+xml");
    public static final String APPLICATION_RSS_XML_VALUE = "application/rss+xml";
    public static final MediaType APPLICATION_STREAM_JSON = valueOf("application/stream+json");
    public static final String APPLICATION_STREAM_JSON_VALUE = "application/stream+json";
    public static final MediaType APPLICATION_XHTML_XML = valueOf("application/xhtml+xml");
    public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
    public static final MediaType APPLICATION_XML = valueOf("application/xml");
    public static final String APPLICATION_XML_VALUE = "application/xml";
    public static final MediaType IMAGE_GIF = valueOf("image/gif");
    public static final String IMAGE_GIF_VALUE = "image/gif";
    public static final MediaType IMAGE_JPEG = valueOf("image/jpeg");
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";
    public static final MediaType IMAGE_PNG = valueOf("image/png");
    public static final String IMAGE_PNG_VALUE = "image/png";
    public static final MediaType MULTIPART_FORM_DATA = valueOf("multipart/form-data");
    public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";
    public static final MediaType TEXT_EVENT_STREAM = valueOf("text/event-stream");
    public static final String TEXT_EVENT_STREAM_VALUE = "text/event-stream";
    public static final MediaType TEXT_HTML = valueOf("text/html");
    public static final String TEXT_HTML_VALUE = "text/html";
    public static final MediaType TEXT_MARKDOWN = valueOf("text/markdown");
    public static final String TEXT_MARKDOWN_VALUE = "text/markdown";
    public static final MediaType TEXT_PLAIN = valueOf("text/plain");
    public static final String TEXT_PLAIN_VALUE = "text/plain";
    public static final MediaType TEXT_XML = valueOf("text/xml");
    public static final String TEXT_XML_VALUE = "text/xml";
    private static final String PARAM_QUALITY_FACTOR = "q";
    public static final Comparator<MediaType> QUALITY_VALUE_COMPARATOR = (mediaType1, mediaType2) -> {
        double quality1 = mediaType1.getQualityValue();
        double quality2 = mediaType2.getQualityValue();
        int qualityComparison = Double.compare(quality2, quality1);
        if (qualityComparison != 0) {
            return qualityComparison;
        } else if (mediaType1.isWildcardType() && !mediaType2.isWildcardType()) {
            return 1;
        } else if (mediaType2.isWildcardType() && !mediaType1.isWildcardType()) {
            return -1;
        } else if (!mediaType1.getType().equals(mediaType2.getType())) {
            return 0;
        } else if (mediaType1.isWildcardSubtype() && !mediaType2.isWildcardSubtype()) {
            return 1;
        } else if (mediaType2.isWildcardSubtype() && !mediaType1.isWildcardSubtype()) {
            return -1;
        } else if (!mediaType1.getSubtype().equals(mediaType2.getSubtype())) {
            return 0;
        } else {
            int paramsSize1 = mediaType1.getParameters().size();
            int paramsSize2 = mediaType2.getParameters().size();
            return paramsSize2 < paramsSize1 ? -1 : (paramsSize2 == paramsSize1 ? 0 : 1);
        }
    };
    public static final Comparator<MediaType> SPECIFICITY_COMPARATOR = new SpecificityComparator<MediaType>() {
        protected int compareParameters(MediaType mediaType1, MediaType mediaType2) {
            double quality1 = mediaType1.getQualityValue();
            double quality2 = mediaType2.getQualityValue();
            int qualityComparison = Double.compare(quality2, quality1);
            return qualityComparison != 0 ? qualityComparison : super.compareParameters(mediaType1, mediaType2);
        }
    };

    public MediaType(String type) {
        super(type);
    }

    public MediaType(String type, String subtype) {
        super(type, subtype, Collections.emptyMap());
    }

    public MediaType(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    public MediaType(String type, String subtype, double qualityValue) {
        this(type, subtype, Collections.singletonMap("q", Double.toString(qualityValue)));
    }

    public MediaType(MediaType other, Charset charset) {
        super(other, charset);
    }

    public MediaType(MediaType other, @Nullable Map<String, String> parameters) {
        super(other.getType(), other.getSubtype(), parameters);
    }

    public MediaType(String type, String subtype, @Nullable Map<String, String> parameters) {
        super(type, subtype, parameters);
    }

    protected void checkParameters(String attribute, String value) {
        super.checkParameters(attribute, value);
        if ("q".equals(attribute)) {
            value = this.unquote(value);
            double d = Double.parseDouble(value);
            Assert.isTrue(d >= 0.0D && d <= 1.0D, "Invalid quality value \"" + value + "\": should be between 0.0 and 1.0");
        }

    }

    public double getQualityValue() {
        String qualityFactory = this.getParameter("q");
        return qualityFactory != null ? Double.parseDouble(this.unquote(qualityFactory)) : 1.0D;
    }

    public boolean includes(@Nullable MediaType other) {
        return super.includes(other);
    }

    public boolean isCompatibleWith(@Nullable MediaType other) {
        return super.isCompatibleWith(other);
    }

    public MediaType copyQualityValue(MediaType mediaType) {
        if (!mediaType.getParameters().containsKey("q")) {
            return this;
        } else {
            Map<String, String> params = new LinkedHashMap(this.getParameters());
            params.put("q", mediaType.getParameters().get("q"));
            return new MediaType(this, params);
        }
    }

    public MediaType removeQualityValue() {
        if (!this.getParameters().containsKey("q")) {
            return this;
        } else {
            Map<String, String> params = new LinkedHashMap(this.getParameters());
            params.remove("q");
            return new MediaType(this, params);
        }
    }

    public static MediaType valueOf(String value) {
        return parseMediaType(value);
    }

    public static MediaType parseMediaType(String mediaType) {
        MimeType type;
        try {
            type = MimeTypeUtils.parseMimeType(mediaType);
        } catch (InvalidMimeTypeException var4) {
            throw new InvalidMediaTypeException(var4);
        }

        try {
            return new MediaType(type.getType(), type.getSubtype(), type.getParameters());
        } catch (IllegalArgumentException var3) {
            throw new InvalidMediaTypeException(mediaType, var3.getMessage());
        }
    }

    public static List<MediaType> parseMediaTypes(@Nullable String mediaTypes) {
        if (!StringUtils.hasLength(mediaTypes)) {
            return Collections.emptyList();
        } else {
            String[] tokens = StringUtils.tokenizeToStringArray(mediaTypes, ",");
            List<MediaType> result = new ArrayList(tokens.length);
            String[] var3 = tokens;
            int var4 = tokens.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String token = var3[var5];
                result.add(parseMediaType(token));
            }

            return result;
        }
    }

    public static List<MediaType> parseMediaTypes(@Nullable List<String> mediaTypes) {
        if (CollectionUtils.isEmpty(mediaTypes)) {
            return Collections.emptyList();
        } else if (mediaTypes.size() == 1) {
            return parseMediaTypes((String)mediaTypes.get(0));
        } else {
            List<MediaType> result = new ArrayList(8);
            Iterator var2 = mediaTypes.iterator();

            while(var2.hasNext()) {
                String mediaType = (String)var2.next();
                result.addAll(parseMediaTypes(mediaType));
            }

            return result;
        }
    }

    public static List<MediaType> asMediaTypes(List<MimeType> mimeTypes) {
        return (List)mimeTypes.stream().map(MediaType::asMediaType).collect(Collectors.toList());
    }

    public static MediaType asMediaType(MimeType mimeType) {
        return mimeType instanceof MediaType ? (MediaType)mimeType : new MediaType(mimeType.getType(), mimeType.getSubtype(), mimeType.getParameters());
    }

    public static String toString(Collection<MediaType> mediaTypes) {
        return MimeTypeUtils.toString(mediaTypes);
    }

    public static void sortBySpecificity(List<MediaType> mediaTypes) {
        Assert.notNull(mediaTypes, "'mediaTypes' must not be null");
        if (mediaTypes.size() > 1) {
            Collections.sort(mediaTypes, SPECIFICITY_COMPARATOR);
        }

    }

    public static void sortByQualityValue(List<MediaType> mediaTypes) {
        Assert.notNull(mediaTypes, "'mediaTypes' must not be null");
        if (mediaTypes.size() > 1) {
            Collections.sort(mediaTypes, QUALITY_VALUE_COMPARATOR);
        }

    }

    public static void sortBySpecificityAndQuality(List<MediaType> mediaTypes) {
        Assert.notNull(mediaTypes, "'mediaTypes' must not be null");
        if (mediaTypes.size() > 1) {
            Collections.sort(mediaTypes, SPECIFICITY_COMPARATOR.thenComparing(QUALITY_VALUE_COMPARATOR));
        }

    }
}
