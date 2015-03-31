package net.floodlightcontroller.dhcpserver;

import java.util.Arrays;

import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.Ethernet;
import org.projectfloodlight.openflow.types.MacAddress;

import java.lang.String;

/**
 * The class representing a DHCP Binding -- MAC and IP.
 * It also contains important information regarding the lease status
 * --active
 * --inactive
 * the lease type of the binding
 * --dynamic
 * --fixed/static
 * and the lease times
 * --start time in seconds
 * --duration in seconds
 * 
 * @author Ryan Izard (rizard@g.clemson.edu)
 */
public class DHCPBinding {
	
	public static final int IP_ADDRESS_LENGTH = 4;
	public static final int MAC_ADDRESS_LENGTH = (int) Ethernet.DATALAYER_ADDRESS_LENGTH;
	
	private byte[] MAC = new byte[MAC_ADDRESS_LENGTH];
	private byte[] IP = new byte[IP_ADDRESS_LENGTH];
	private boolean LEASE_STATUS;
	private boolean PERMANENT_LEASE;
	
	private long LEASE_START_TIME_SECONDS;
	private long LEASE_DURATION_SECONDS;
	
	protected DHCPBinding(byte[] ip, byte[] mac) {
		this.setMACAddress(mac);
		this.setIPv4Addresss(ip);
		this.setLeaseStatus(false);
	}
	
	public byte[] getIPv4AddressBytes() {
		return IP;
	}
	
	public String getIPv4AddresString() {
		return IPv4.fromIPv4Address(IPv4.toIPv4Address(IP));
	}
	
	public byte[] getMACAddressBytes() {
		return MAC;
	}
	
	public String getMACAddressString() {
		System.out.println("getMacAddress of : " + MAC + " is : " + new String(MAC));
		return new String(MAC);
	}
	
	private void setIPv4Addresss(byte[] ip) {
		IP = Arrays.copyOf(ip, IP_ADDRESS_LENGTH); 
	}
	
	public void setMACAddress(byte[] mac) {
		MAC = Arrays.copyOf(mac, MAC_ADDRESS_LENGTH);
	}
	
	public void setMACAddress(String mac) {
		MAC = Ethernet.toMACAddress(mac);
	}
	
	public boolean isActiveLease() {
		return LEASE_STATUS;
	}
	
	public void setStaticIPLease(boolean staticIP) {
		PERMANENT_LEASE = staticIP;
	}
	
	public boolean isStaticIPLease() {
		return PERMANENT_LEASE;
	}
	
	public void setLeaseStatus(boolean status) {
		LEASE_STATUS = status;
	}
	
	public boolean isLeaseExpired() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime / 1000) >= (LEASE_START_TIME_SECONDS + LEASE_DURATION_SECONDS)) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void setLeaseStartTimeSeconds() {
		LEASE_START_TIME_SECONDS = System.currentTimeMillis() / 1000;
	}
	
	protected void setLeaseDurationSeconds(long time) {
		LEASE_DURATION_SECONDS = time;
	}
	
	protected void clearLeaseTimes() {
		LEASE_START_TIME_SECONDS = 0;
		LEASE_DURATION_SECONDS = 0;
	}
	
	protected boolean cancelLease() {
		this.clearLeaseTimes();
		this.setLeaseStatus(false);
		return true;
	}
}
