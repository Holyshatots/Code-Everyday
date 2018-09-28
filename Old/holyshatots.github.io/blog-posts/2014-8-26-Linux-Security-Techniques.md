---
layout: post
title: Linux Security Techniques
date:   2014-08-26 00:00:00
---

Basic definitions:

- Subject: a user or a process carrying out work on behalf of a user.
- Object: A particular resource on a computer erver, such as a database or a
hardware device.
- Authentication: Determining a subject is who he says he is. This is sometimes
called "identification and authentication."
- Acces control: Controlling a subject's ability to use a particular object.
- Access Control List (ACL): A list of subjects who may acess a particular object.

### Planning phase

3 types of access control:

- Discretionary Access Control (DAC): The traditional Linux security access
control. Acess to an object is based on a subject's identity and group
memberships.
- Mandatory Access Control (MAC): Overcomes weaknesses with DAC. Objects are
classified by their need for data integrity and privacy. Subjects are classified
by their data or security clearence level. A subject's access to an object is
based upon the subject's clearence level.
- Role Based Access Control List (RBAC): Refinement to the MAC model. Access to
an object is baed upon a subject's identity and its associated role. Each role,
not each subject, is classified by its data or security clearence leve. Thus, 
access to objects is based upon a role's clearence level and not an individual
subject's clearence level.

### Implementation Phase

__Physical Security__

- A lock or security alarm on the server room door.
- Access control that llow only authorized access and identify who accessed the 
room and identify who accessed the room and when the acess occured, such as a 
key entry system. 
- A sign stating "no unauthorized access allowed" on the door.
- Policies on who can access the room and when access may occur, for groups such
as the cleaning crew, server administration, and others.
- Approppriate fire suppression controls.
- Clean Desk Policy (CDP): All papers must be locked away at the end of the day.
- A "no written passwords" policy

__Disaster Recovery__

- What data is to be included in backups
- Where backups are to be stored
- How long backups are maintained
- How backup media is rotated through storage

__Securing User Accounts__

- One user per user account
- No logins to the root account
- Set expiration dates on temporary accounts
- Remove unused user accounts

Note:

repudiation: The user can deny actions or refuse accountability.

non-repudiation: The user cannot deny actions or refuse accountability.

__Passwords__

Early Linux stored hashed passswords in the /etc/passwd file. 

Salting a hash: A randomly generated value is added to the original password
before it is hashed.

New Linux systems store hashed passwords in the /etc/shadow which only
root can access.

__Securing the filesystem__

- The /etc/fstab file should have the following permission settings:
  - Owner: root
  - Group: root
  - Permission: (644) rw-r--r--
- Put the /home directory, where user directories are located, on its own 
partition and:
  - Set the nosuid option to prevent SUID and SGID permission-enabled executeable
  programs running from there. Programs that need SUID and SGID permissions should
not be stored in /home and are most likely malicious
  - Set the nodev option so no device file located there will be recognized.
  Device files should be stored in /dev and not /home.
  - Set the noexec option so no executeable programs, which are stored in /home,
  can be run. Executeable programs should not be stored in /home and are most
  likely malicious.
- Put the /tmp subdirectory, where temp files are located, on its own partition
and use the same options settings as for /home.
- Put the /usr subdirectory, where user programs and data are lcoated, on its
own partition and set the no dev option so no device file loated there will 
be recognized.
- Put the /var subdirectory, where important security log files are located, on
its own partition and use the same options settings as for /home.

###  Monitoring Phase

- Most of the log files are display using cat, head, tail, more, or les. Some
require special commands for viewing.

| Filename | View command  |
|----------|-------------- |
| btmp     | dump-utmp btmp|
| dmesg    | dmesg         |
| lastlog  | lastlog       |
| wtmp     | dump-utmp wtmp|

-Review the rsyslog daemon's configuration file, /etc/rsyslog.conf, to ensure 
the desired information si being tracked.
  - Track any modifications to /etc/rsyslogd.conf
  - Review the /etc/rsyslogd.conf to ensure that login attempts are sent to 
/var/log/secure by the setting authpriv* /var/log/secure.
- Create log alerts. For example, a sudden increae in a log file size, such as 
/var/log/btmp is an indicator of a potentially malicious problem.
- Maintain log file integrity
  - Review log file permissions
  - Review the log rotation schedule in the logrotate configuration, on file
/etc/logrotate.conf
  - Implement best practices, such as clearing log files appropriately so as not
to lose file permission settings. I.E.: `> logfilename`

__Monitoring User Accounts__

Use auditctl command to monitor the /etc/passwd and /etc/group files. Two
options at minimum are required to start the process.

- w filename : Place a watch on filename
- p trigger(s) : If one of these access types occurs ( r=read, w=write, x=execute,
a=attribute change) to filename, then tirgger an audit record.

Audit daemon tools:

- auditd : the audit daemon
- auditd.conf : Audit daemon configuration file
- auditctl : Controls the auditing system
- audit.rules : Configuration rules loaded at boot
- ausearch : Searches the audit logs for specified items
- aureport : Report creator for the audit logs
- audispd : Sends audit information to other programs

__Detecting viruses and rootkits__

Viruses: Scan with clamav

Rootkits: Scan with chrootkit

__Detecting an Intrusion__

Popular Linux IDSs :
- aide
- Snort
- tripwire

Aide creates a database of the initial system and then later on it can be used
to determine the changes by making another database and comparing them.

__Audit/Review Phase__

- compliance review : an audit of the overall computer system environment to
ensure policies and procedures determined in the Security Process Lifecycle
Planning phase are being carried out correctly.
- security review : an audit of the current policies and proceduure sto ensure
they follow accepted best security practices.

## Basic Guide

### Change Password

`passwd`

Changes the password, remember to change the root password to something secure.

### Update System

`apt-get update && apt-get upgrade`

### Install fail2ban

`apt-get install fail2ban`

### Setup up login user

`useradd deploy`
`mkdir /home/deploy`
`mkdir /home/deploy/.ssh`
`chmod 700 /home/deploy/.ssh`

### Require public key authentication

`nano /home/deploy/.ssh/authorized_keys

Add the contents of the id_rsa.pub on your local machine.

`chmod 400 /home/deploy/.ssh/authorized_keys`
`chown deploy:deploy /home/deploy -R`

### Test the New User & Enable Sudo

`passwd deploy`

Set a secure password for the user.

`visudo`

Add the following:

`root ALL=(ALL) ALL`
`deploy ALL=(ALL) ALL`

### Lock Down SSH

`nano /etc/ssh/sshd_config`

Add these lines to the file, inserting the ip address from where you will be connecting. 

`PermitRootLogin no`
`PasswordAuthentication no`
`AllowUser deploy@(your-ip) deploy@(another-more-ips)`

Restart ssh service.

`sudo service ssh restart`

### Set Up A Firewall

By default ubuntu contains ufw, it can be installed in other distros with:

`sudo apt-get instal ufw`

Then to setup the firewall:

`ufw allow from {your-ip} to any port 22`
`ufw allow 80`
`ufw allow 443`
`ufw enable`

This will allows connection over port 80 and 443. 

### Enable Automatic Security Updates

```sudo apt-get install unattended-upgrades
sudo nano /etc/apt/apt.conf.d/10periodic```

Update the file to look like this:

```APT::Periodic::Update-Package-Lists "1";
APT::Periodic::Download-Upgradeable-Packages "1";
APT::Periodic:AutocleanInterval "7";
APT::Periodic::Unattended-Upgrade "1";```

`sudo nano /etc/apt/apt.conf.d/50unattended-upgrades`

Update the file to look like this:

```Unattended-Upgrade::Allowed-Origins {
  "Ubuntu lucid-security";
//  "Ubuntu lucid-updates";
};```

### Install Logwatch

Logwatch emails logs to you.

`sudo apt-get install logwatch`
`sudo nano /etc/cron.daily/00logwatch`

Add the following line:

`sudo /usr/sbin/logwatch --output mail --mailto admin@example.com --detail high`