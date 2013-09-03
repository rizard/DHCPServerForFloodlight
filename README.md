DHCPServerForFloodlight
=======================
Ryan Izard
rizard@g.clemson.edu
Clemson University

Based on Floodlight v0.90, this is a currently single-subnet DHCP server for the Floodlight OpenFlow controller. It is implemented in the module net.floodlightcontroller.dhcpserver located in src/main/java/. Configuration of the DHCP server can be customized in floodlightdefault.properties located in src/main/resources/.

The DHCP server works by intercepting DHCP packets on any OpenFlow enabled switch connected to Floodlight. The switch forwards the DHCP packet to Floodlight, at which point the DHCP Server module in Floodlight will handle the DHCP message and send a response out the port on which the original packet was received.

Expansion on this project is planned to allow for multiple subnets based on switch DPIDs and/or switch port numbers. Furture work will also allow traditional DHCP servers to function in conjunction with this SDN-based approach.

