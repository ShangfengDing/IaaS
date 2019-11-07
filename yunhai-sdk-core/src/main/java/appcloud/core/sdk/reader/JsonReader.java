package appcloud.core.sdk.reader;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;

import appcloud.core.sdk.exceptions.ClientException;

public class JsonReader implements Reader {

	private static final Object ARRAY_END_TOKEN = new Object();
    private static final Object OBJECT_END_TOKEN = new Object();
    private static final Object COMMA_TOKEN = new Object();
    private static final Object COLON_TOKEN = new Object();

    private static final int FIRST_POSITION = 0;
    private static final int CURRENT_POSITION = 1;
    private static final int NEXT_POSITION = 2;

    private CharacterIterator ct;
    private char c;
    private Object token;
    private StringBuffer stringBuffer = new StringBuffer();
    private Map<String, String> map = new HashMap<String, String>();
    
    private static Map<Character, Character> escapes = new HashMap<Character, Character>();
    static {
    	escapes.put(Character.valueOf('\\'), Character.valueOf('\\'));
        escapes.put(Character.valueOf('/'), Character.valueOf('/'));
        escapes.put(Character.valueOf('"'), Character.valueOf('"'));
        escapes.put(Character.valueOf('t'), Character.valueOf('\t'));
        escapes.put(Character.valueOf('n'), Character.valueOf('\n'));
        escapes.put(Character.valueOf('r'), Character.valueOf('\r'));
        escapes.put(Character.valueOf('b'), Character.valueOf('\b'));
        escapes.put(Character.valueOf('f'), Character.valueOf('\f'));  
    }
	@Override
	public Map<String, String> read(String response, String endpoint)
			throws ClientException {
		// TODO Auto-generated method stub
		return read(new StringCharacterIterator(response), endpoint, FIRST_POSITION);
	}
	private Map<String, String> read(StringCharacterIterator stringCharacterIterator, String endpoint, int firstPosition) {
		// TODO Auto-generated method stub
		ct = stringCharacterIterator;
		switch(firstPosition) {
		case FIRST_POSITION:
			c = ct.first();
			break;
		case CURRENT_POSITION:
			c = ct.current();
			break;
		case NEXT_POSITION:
			c = ct.next();
			break;
		}
		readJson(endpoint);
		return map;
	}
	private Object readJson(String baseKey) {
		// TODO Auto-generated method stub
		skipWhiteSpace();
        char ch = c;
        nextChar();
        switch (ch) {
	        case '{': processObject(baseKey); break;
	        case '}': token = OBJECT_END_TOKEN; break;
            case '[': 
            	if(c == '"') {
            		processList(baseKey); break;
            	}
            	else {
            		processArray(baseKey); break;
				}
            case ']': token = ARRAY_END_TOKEN; break;
            case '"': token = processString(); break;
            case ',': token = COMMA_TOKEN; break;
            case ':': token = COLON_TOKEN; break;
            case 't':
                nextChar(); nextChar(); nextChar(); 
                token = Boolean.TRUE;
                break;
            case 'n':
                nextChar(); nextChar(); nextChar(); 
                token = null;
                break;
            case'f':
                nextChar(); nextChar(); nextChar(); nextChar(); 
                token = Boolean.FALSE;
                break;
            default:
                c = ct.previous();
                if (Character.isDigit(c) || c == '-') {
                    token = processNumber();
                }
        }
        return token;
	}
	private void processObject(String baseKey) {
		// TODO Auto-generated method stub
		String key = baseKey + "." + readJson(baseKey);
		while (OBJECT_END_TOKEN != token) {
			readJson(key);
			if(OBJECT_END_TOKEN != token) {
				Object object = readJson(key);
				if(object instanceof String || object instanceof Number || object instanceof Boolean) {
					map.put(key, String.valueOf(object));
				}
				if(COMMA_TOKEN == readJson(key)) {
					key = String.valueOf(readJson(key));
					key = baseKey + "." + key;
				}
			}
		}
		
	}

	private void processList(String baseKey) {
		// TODO Auto-generated method stub
		Object value = readJson(baseKey);
		int index = 0;
		while (ARRAY_END_TOKEN != token) {
			String key = trimFormLast(baseKey, ".") + "[" + (index++) + "]";
			map.put(key, String.valueOf(value));
			if(COMMA_TOKEN == readJson(baseKey)) {
				value = readJson(baseKey);
			}
		}
		map.put(trimFormLast(baseKey, ".") + ".Length", String.valueOf(index));
		
	}

	private void processArray(String baseKey) {
		// TODO Auto-generated method stub
		int index = 0;
    	String preKey = baseKey.substring(0,baseKey.lastIndexOf("."));
    	String key =  preKey + "["+ index +"]";
	    Object value = readJson(key);

	    while (token != ARRAY_END_TOKEN) {
	    	map.put(preKey + ".Length" , String.valueOf(index + 1));
		    if(value instanceof String){
		    	map.put(key, String.valueOf(value));
		    }
	    	if (readJson(baseKey) == COMMA_TOKEN) {
	    		key =  preKey + "["+ (++index) +"]";
	    		value = readJson(key);
	    	}
	    }
	}
	private Object processNumber() {
		// TODO Auto-generated method stub
		stringBuffer.setLength(0);
        if ('-' == c) {
            addChar();
        }
        addDigits();
        if ('.' == c) {
            addChar();
            addDigits();
        }
        if ('e' == c || 'E' == c) {
            addChar();
            if ('+' == c  || '-' == c) {
                addChar();
            }
            addDigits();
        } 
        return stringBuffer.toString();
	}
	private void addDigits() {
		// TODO Auto-generated method stub
		while (Character.isDigit(c)) {
    		addChar();
		}
	}
	private Object processString() {
		// TODO Auto-generated method stub
		stringBuffer.setLength(0);
		while('"' != c) {
			if('\\' != c) {
				nextChar();
				Object value = escapes.get(Character.valueOf(c));
				if(null != value) {
					addChar(((Character) value).charValue());
				}
			}else {
				addChar();
			}
		}
		nextChar();
		return stringBuffer.toString();
	}
	private void addChar() {
		// TODO Auto-generated method stub
		addChar(c);
	}
	private void addChar(char charValue) {
		// TODO Auto-generated method stub
		stringBuffer.append(charValue);
		nextChar();
	}

	private void skipWhiteSpace() {
		// TODO Auto-generated method stub
		while(Character.isWhitespace(c)) {
			nextChar();
		}
	}
	private char nextChar() {
		// TODO Auto-generated method stub
		c = ct.next();
		return c;
	}
	private String trimFormLast(String string, String stripString) {
		// TODO Auto-generated method stub
		int pos = string.lastIndexOf(stripString);
		if(pos > -1) {
			return string.substring(0, pos);
		}else {
			return string;
		}
	}
}
