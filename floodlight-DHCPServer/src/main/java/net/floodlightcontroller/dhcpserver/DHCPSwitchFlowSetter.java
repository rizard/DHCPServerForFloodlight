package net.floodlightcontroller.dhcpserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.floodlightcontroller.core.PortChangeType;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.linkdiscovery.ILinkDiscovery;
import org.projectfloodlight.openflow.protocol.OFActionType;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.OFPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IOFSwitchListener;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.UDP;

import net.floodlightcontroller.staticflowentry.IStaticFlowEntryPusherService;
// Adding a comment to test a new commit
public class DHCPSwitchFlowSetter implements IFloodlightModule, IOFSwitchListener {
	protected static Logger log;
	protected IFloodlightProviderService floodlightProvider;
	protected IOFSwitchService switchService;
	protected IStaticFlowEntryPusherService sfp;
	
	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l = 
				new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		l.add(IStaticFlowEntryPusherService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		switchService = context.getServiceImpl(IOFSwitchService.class);
		log = LoggerFactory.getLogger(DHCPServer.class);
		sfp = context.getServiceImpl(IStaticFlowEntryPusherService.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context) {
	}

	@Override
	public void switchAdded(DatapathId swid) {



/*
		IOFSwitch sw = switchService.getSwitch(swid);
		if (sw.getEnabledPortNumbers() != null) {
			for (OFPort p : sw.getEnabledPortNumbers()) {
				processNewPort(sw.getId(), p);
			}
		}
		ILinkDiscovery.LDUpdate update = new ILinkDiscovery.LDUpdate(sw.getId(), ILinkDiscovery.SwitchType.BASIC_SWITCH, ILinkDiscovery.UpdateOperation.SWITCH_UPDATED);
		updates.add(update);
*/
		System.out.println("TODO: ADD STATIC RULE HERE?");

		/** Insert static flows on all ports of the switch to redirect
		 * DHCP client --> DHCP DHCPServer traffic to the controller.
		 * DHCP client's operate on UDP port 67

		OFFlowMod flow = new OFFlowMod();
		//OFMatch match = new OFMatch();
		ArrayList<OFAction> actionList = new ArrayList<OFAction>();
		OFAction action = new OFAction();
		for (OFPort port : swid..getPorts()) {
			match.setInputPort(port.getPortNumber());
			match.setDataLayerType(Ethernet.TYPE_IPv4);
			match.setNetworkProtocol(IPv4.PROTOCOL_UDP);
			match.setTransportSource(UDP.DHCP_CLIENT_PORT);
			action.setType(OFActionType.OUTPUT);
			action.setLength((short) 8);
			actionList.add(action);
			
			flow.setCookie(0);
			flow.setBufferId(-1);
			flow.setHardTimeout((short) 0);
			flow.setIdleTimeout((short) 0);
			flow.setOutPort(OFPort.OFPP_CONTROLLER);
			flow.setActions(actionList);
			flow.setMatch(match);
			flow.setPriority((short) 32768);
			sfp.addFlow("dhcp-port---"+port.getPortNumber()+"---("+port.getName()+")", flow, sw.getStringId());
		}
		 */
	}

	public void removedSwitch(IOFSwitch sw) {
	}

	public void switchPortChanged(Long switchId) {
	}

	public String getName() {
		return DHCPSwitchFlowSetter.class.getSimpleName();
	}



	@Override
	public void switchRemoved(DatapathId switchId) {

	}

	@Override
	public void switchActivated(DatapathId switchId) {

	}

	@Override
	public void switchPortChanged(DatapathId switchId, OFPortDesc port, PortChangeType type) {

	}

	@Override
	public void switchChanged(DatapathId switchId) {

	}
}