package lazygom.webslowcancel;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class WSCDummyContainer extends DummyModContainer {
	
	public WSCDummyContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "webslowcancel";
		meta.name = "WebSlowCancel";
		meta.version = "1.7.10-180121";
		meta.credits = "";
		meta.authorList = Arrays.asList(new String[] {"LazyGom"});
		meta.description = "";
		meta.url = "";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

}
