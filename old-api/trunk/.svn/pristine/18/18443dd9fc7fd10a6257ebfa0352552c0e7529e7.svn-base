package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import tip.util.log.Log;
import appcloud.api.client.constants.Constants;
import appcloud.api.exception.ServerException;

import com.sun.jersey.api.Responses;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author jianglei, xuanjiaxing
 *
 * @param <T> A java bean the client deal with;
 */
public abstract class AbstractClient<T> {

	protected final static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	protected WebResource resource;
	
	protected abstract Class<?> getType();
	protected abstract GenericType<List<T>> getGenericType();
	
	public AbstractClient() {
		this(Constants.BASE_URI);
	}
	
	public AbstractClient(String baseURI) {
		ClientConfig cc = new DefaultClientConfig();
	    Client c = Client.create(cc);
	    resource = c.resource(baseURI);
	}
	
	@SuppressWarnings("unchecked")
	protected T get(String path) {
		try{
			return (T) resource.path(path).get(getType());
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("get Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			} else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}
	
	protected List<T> getList(String path, MultivaluedMap<String, String> params) {
		try {
			if(params == null){
				params =  new MultivaluedMapImpl();
			}
			print("path:"+path+" params:"+params);
			return (List<T>) resource.path(path).queryParams(params).get(getGenericType());
		}catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("getlist Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}	
	
	@SuppressWarnings("unchecked")
	protected T update(String path, Object cReq) {
		try{
			return (T) resource.path(path).type(MediaType.APPLICATION_XML).put(getType(), cReq);
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("update Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected T postWithRet (String path, Object cReq) {
		try{
			return (T) resource.path(path).type(MediaType.APPLICATION_XML).post(getType(), cReq);
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("postwithret Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
				//return null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> postWithRetList (String path, Object cReq) {
		try{
			return (List<T>) resource.path(path).type(MediaType.APPLICATION_XML).post(getType(), cReq);
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("postWithRetList Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
				//return null;
			}
		}
	}
	
	protected boolean postWithoutRet (String path, Object req) {
		try{
			resource.path(path).type(MediaType.APPLICATION_XML).post( req);
			return true;
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("postWithoutRet Item Not Found: " + ue.getResponse().getEntity(String.class));
				return false;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return false;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}
	
	protected Object otherGet(Class<?> type, String path) {
		try{
			return resource.path(path).get(type);
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("otherGet Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return null;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}

	protected boolean delete (String path) {
		try {
			Log.info("in api-client delete image path:"+path);
			resource.path(path).delete();
			return true;
		}
		catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("delete Item Not Found: " + ue.getResponse().getEntity(String.class));
				return false;
				//throw new ItemNotFoundException();
			}
			else if(ue.getResponse().getStatus() == Responses.CONFLICT){
				print("Input error: " + ue.getResponse().getEntity(String.class));
				return false;
				//throw new OperationFailedException();
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}
	
	protected Long count(String path, MultivaluedMap<String, String> params) {
		try {
			if(params == null){
				params =  new MultivaluedMapImpl();
			}
			return Long.valueOf( resource.path(path).queryParams(params).get(String.class));
		}catch(UniformInterfaceException ue) {
			if(ue.getResponse().getStatus() == Responses.NOT_FOUND){
				print("count Item Not Found: " + ue.getResponse().getEntity(String.class));
				return null;
			}
			else if(ue.getResponse().getStatus() == 500){
				print("Internal Server Error: " + ue.getResponse().getEntity(String.class));
				throw new ServerException();
			}
			else {
				print("Unknow Error");
				throw new RuntimeException("I do not know what is wrong");
			}
		}
	}	
	
	protected void print(Object obj) {
		System.out.println(obj);
	}
	/*public void haha(String path) {
		print("hahapath:"+path);
		resource.path(path).head();
		
	}*/
	
}
