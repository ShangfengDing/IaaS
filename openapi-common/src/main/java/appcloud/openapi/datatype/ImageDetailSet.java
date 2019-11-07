package appcloud.openapi.datatype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ImageDetailSet {

	private List<ImageDetailItem> ImageDetailItems;

	public ImageDetailSet(){}

	public ImageDetailSet(List<ImageDetailItem> ImageDetailItems) {
	    setImageDetailItems(ImageDetailItems);
	}

	@XmlElement(name="ImageDetail")
    public List<ImageDetailItem> getImageDetailItems() {
        return ImageDetailItems;
    }

    public void setImageDetailItems(List<ImageDetailItem> imageDetailItems) {
        ImageDetailItems = imageDetailItems;
    }

	
}
