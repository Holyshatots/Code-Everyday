# Can Permissions

A matrix permissions and authentication systems for CFWheels.

Checks if the user is logged in and that they have the necessary permissions.
The permissions that they have are stored in `session.user.group`.

## Installation

1. Setup up the required database tables (see below)
2. Download or clone this repository
3. Zip up the directory into CanPermission-0.1.Zip
4. Place in the plugins/ directory of your CFWheels installation

### Required database tables:

Setup the following tables in your database.

groups
  - id (integer)

permissions
  - id (integer)
  - name (varchar)

grouppermissions
  - groupid (integer)
  - permissionid (integer)

### Suggested database tables:

users
  - id
  - groupid
  - username

## Example Usage

Say the database tables look like:

groups

| id | name |
| --- | --- |  
| 1 | superuser |
| 2 | normal |


permissions

|id | name|
| --- | --- |
|1 | ShowUser|
|2 | CreateUser|
|3 | DeleteUser|
|4 | CreateBook|
|5 | UpdateBook|

grouppermissions

| groupid | permissionid |
| ------- | ------------ |
| 1 | 1 |
| 1 | 2 |
| 1 | 3 |
| 1 | 4 |
| 1 | 5 |
| 2 | 4 |
| 2 | 5 |

```
// In your login
populatePermissions(1);


// In Book controller's init()
// This will check for the permission CreateBook in the session and if it
// doesn't exist, render AccessDenied() template. If it does exist then allows  
// the call through to the create() function.
<cffunction name="init">
    <cfset filters(through="canOrDenied", permission="CreateBook", only="create")>
</cffunction>

// Also possible, this will let the user through to index if they have at least
// one of the permissions. Otherwise, will render the AccessDenied() template.
<cffunction name="init">
    <cfset filters(through="canOrDeniedMulti", permissions=["CreateBook","ViewBook","UpdateBook"], only="index")>
</cffunction>

// In a view or elsewhere
<cfif can("DeleteUser")>
    <button>Delete User</button>
</cfif>

<cfif isLoggedIn()>
    <i>#username#</i>
</cfif>

// In a controller action AccessDenied() can be used anywhere. It is
// encouraged to call it in a return because otherwise funky stuff can happen.
<cfif user.money lt 20.10>
    <cfreturn AccessDenied()>
</cfif>


// In your logout
clearPermissions();

```

## Function Reference

### can()

Assuming that you have created a permission with the name "readFile" and id of 1.

```
if(can("readFile")) {
    // Do authenticated things for readFile

} else {
    // Do something else
}
```

### canOrDenied()

```
canOrDenied("readFile");

// If the user isn't logged in or doesn't have this permission, won't reach
// this code
```

### canOrDeniedMulti()

```
canOrDeniedMulti(["CreateUser,UpdateUser,ShowUser"]);

// If the user has at least one of these this code will be reached,
// otherwise the AccessDenied() function will be called.
```

### isLoggedIn()

```
if(isLoggedIn()) {
    // Do logged in things
}
```

### AccessDenied()

```
return AccessDenied();

// Will display the access denied template defined at
// application.AccessDeniedHandler or "/views/shared/accessdenied.cfm"
// otherwise.
```

### populatePermissions()

```
populatePermissions(user.groupid);

// The session.user.group struct will be created and the permissions will be
// populated from the database tables.

```

### clearPermissions()

```
clearPermissions();

// Clears session.user.group so that there are no valid permissions for the
// current session.

```
