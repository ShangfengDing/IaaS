package appcloud.core.sdk.profile;

import java.util.List;

import appcloud.core.sdk.auth.Credential;
import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.regions.Endpoint;

public interface YhaiClientProfile {

	public String getRegionId();

	public String getZoneId();

	public FormatType getFormat();

	public Credential getCredential();

	public List<Endpoint> getEndpoints() throws ClientException;
}
