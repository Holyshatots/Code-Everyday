---
layout: post
title: "Securing Linux on a Network"
date:   2014-08-27 00:00:00
---

Nmap is a great port-scanning tool to discover services running
on remote machines. Depending on the settings of the remote device, the
Operating System can be determined. 

There are many tools to simplify the process of using nmap like the 
GUI tool, zenmap. However, using [manual port scanning techniques](http://nmap.org/book/mna-port-scanning-techniques.html)
properly can give information that may not have been apparent before.

There are two main types of scans depending on the type of port you want to test for:

- TCP connect port scan: Attempts to connect using TCP and handshaking with the
server
- UDP port scan : Sends a UDP packet to every port and sees if it replies

After scanning a remote machine, nmap will generate a report of what it 
has discovered. Each port will have one of the following statuses:

- OPEN : A server has a service handling requests on the port
- CLOSED : A closed port is accessible but there is no service running on the port.
- FILTERED : A closed port is accessible but there is no service running on
the port.
- UNFILTERED : nmap scan sees the port but cannot determine if it is open or
closed.
- OPEN | FILTERED : nmap scan sees the port, but cannot determine if the port is
open or filtered.
- CLOSED | FILTERED : nmap scan sees the port but cannot determine if the port 
is closed or filtered.

### Controlling access to network services

For Linux systems that support TCP wrapper access is controlled via the
/etc/hosts.allow and /etc/hosts.deny. If a network service has TCP wrapper support,
it will user libwrap. To check, run the *ldd* command on the network service 
daemon. If nothing is shown, then no output will be displayed.

Steps TCP wrapper takes:

1. The hosts.allow file is checked
  - If the remote system's address is listed:
    - Access is allowed
    - No further TCP wrapper checks are made
2. The hosts.den file is checked
  - If the remote system's address is listed access is denied.
  - If the remote system's address is not listed, access is allowed.

### Working with firewalls

Firewall categories:

- A firewall is either __network__ or __host-based__.
- A firewall is either a __hardware__ or __software firewall__.
- A firewall is either a network or application-layer filter:
  - A __network-layer firewall__ works on the basis of individual packets. Also called
a packet filter.
  - An __application-layer firewall__ works on the higher levels of the OSI model.
Only allows certain applications access to/from the system.

### Implementing firewalls

iptablesbasics:

- Tables
- Chains
- Policies
- Rules

Tables functionality:

- filter : packet filtering feature
- nat : Used for NAT (network address translation)
- mangle : packets are modified according to the rules in the mangle table.
- raw - Used to exempt certain network packets from something called "connection
tracking" Important when using NAT and virtualization.
- security : Only available on Linux distros with SELinux. The security table
is used to filter network packets using MAC rules.

There are 5 chains (categories) that a network packet can be designated as:

- INPUT : Network packets coming into the Linux server
- OUPUT : Network packets coming out of the Linux server
- FORWARD : Network packets coming into the LInux server that are to be routed
elsewhere.
- PREROUTING : Used by NAT, for modifying network packets before they come out
of a Linux server
- POSTROUTING : Used by NAT, for modifying network packets before they come out
of the Linux server.

Chains available for each netfilter/iptables table:

- filter : INPUT, FORWARD, OUTPUT
- nat : PREROUTING, OUTPUT, POSTROUTING
- mangle : INPUT, FORWARD, PREROUTING, OUTPUT, POSTROUTING
- raw : PREROUTING OUTPUT
- security : INPUT, FORWARD, OUTPUT
- 
A packet is categorized through several method. If no rule exists for a
particular packet, the overall policy is used. Each category/chain has a default 
policy. Once it matches a rule an action can be taken. 

Some possible actions:

- ACCEPT : Network packet is accepted into the server
- REJECT : Network packet is dropped and not allowed into the server. A rejection
message is sent.
- DROP : Network packet is dropped and not allowed into the server. No rejection
message is sent. 

### Using iptables

To see what policies are currently in place: `iptables -t filter -L`

Note: ipv6 packets are dealt with using the ipables6 utility. Same options forz  
modifying netfilter/iptables.

`-t table` : The command along with the switch is applied to the table.

Flush an iptables interface:

`iptables -F`

Note: this doesn't effect the config file and it can be restored with iptables-restore
< /etc/sysconfig/iptables

Note: Ubuntu differs on saving/ restoring iptables

