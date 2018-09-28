---
layout: post
title: Linux Encryption
date:   2015-01-15 00:00:00
---

###Cryptography

- __Plaintext__: Text that a human or a machine can read an comprehend
- __Ciphertext__: Text that a human or machine cannot read and comprehend
- __Encryption__: Process of converting plaintext into cipher text using an algorithm.
- __Decryption__: The process of converting cipher text into plaintext using an algorithm.
- __Cipher__: The algorithm used to encrypt plaintext into cipher text and decryptcipher text into plaintext.
- __Block cipher__: A cipher that breaks data into blocks before encrypting.
- __Stream cipher__: A cipher that encrypts the data without breaking it up.
- __Key__: A piece of data required by the cipher to succesfully encrypt or decrypt data.

###Creating encrypted files with gpg

Symmetric key:

```bash
gpg -c secret.file (encrypt)
gpg -d secret.file.gpg (decrypt)
```

Asymmetric keys:

```bash
gpg --gen-key (Generate key pair)
gpg --export John Doe > JohnDoe.pub (export public key)
gpg --import JohnDoe.pub (import public key)
gpg --out MessageForJohn --recipient "John Doe" --encrypt MessageForJohn.txt (encrypts message with public key)
gpg --out ChristinesMessage --decrypt MessageForJohn (decrypt message)
```

###Encrypting on a Linux Filesystem

There is an option during Linux installation to encrypt the root partition.
- user crypts to get more information about a system's encrypted volumes.

###Encrypt a Linux directory

Use ecryptfs to encrypt a directory

`mount -t ecryptfs /home/johndoe/Secret /home/johndoe/Secret`

To see a full list of crypto tools on your linux system use: `man -k crypt`

### Implementing Linux Security with PAM

PAM (Pluggable Authentication Modules) was invented b Sun Microsystems and
originally implemented in the Solaris operating system. Applications have to be
built to be PAM-aware. To check if one is use: `ldd programname | grep pam`

### The PAM authentication process

1. A subject (user or process) requests access to an application.
2. The application's PAM configuration file, which contains an access policy,
is open and read. The access policy is set via a list of all the PAM modles to 
bue used in the authentication process. This PAM module(s) list is called a
__stack__.
3. Each PAM module in the stack in invoked in the order it is listed.
4. Each PAM module returns either a success or failure status.
5. The stack continues to be read in oreder and is not necesarily stopped by a 
single returned failure status.
6. The status results of all the AM modules are combined into a single overall 
result of authentication or failure.

All PAM config files are located in /etc/pam.d
