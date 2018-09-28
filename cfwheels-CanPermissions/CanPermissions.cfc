<!---
 Can Permissions
 ===============
 A matrix permission system for CFWheels.
 Github: https://github.com/themcaffee/cfwheels-CanPermissions
--->

<cfcomponent output="false">

	<cffunction name="init">
		<cfset this.version = "1.0,1.0.1,1.0.2,1.0.3,1.0.4,1.0.5,1.1,1.1.1,1.1.2,1.1.3,1.1.4,1.1.5">
		<cfreturn this>
	</cffunction>

	<cffunction name="can"
	    description="Returns true if the group has permission for the given action." returntype="boolean">
	    <cfargument name="action" type="string" required="yes" hint="The permission that will be checked in the database.">

	    <cfreturn $boolify(isDefined("session.user.group.#action#") AND session.user.group["#action#"] neq ''
	        AND session.user.group["#action#"])>
	</cffunction>

	<cffunction name="canOrDenied"
		description="Checks if the user can do something and if not will redirect">
		<cfargument name="permission" required="yes" type="string" hint="The permission that will be checked in the database.">

		<cfif !can(permission)>
			<cfset AccessDenied()>
		</cfif>
	</cffunction>

	<cffunction name="canOrDeniedMulti"
		description="Checks if the user has any of the permissions. If the user
		doesn't contain a single given permission, it will redirect to Access Denied">
		<cfargument name="permissions" required="yes" type="array" hint="An array of strings that map to permissions">

		<cfloop array="#permissions#" index="i">
			<cfif can(i)>
				<cfreturn>
			</cfif>
		</cfloop>

		<!--- No permission matched so access denied --->
		<cfset AccessDenied()>
	</cffunction>

	<cffunction name="isLoggedIn" description="Returns true if the user is logged in">
	    <cfif isDefined("session.user.group")>
	        <cfreturn true>
	    <cfelse>
	        <cfreturn false>
	    </cfif>
	</cffunction>

	<cffunction name="AccessDenied"
		description="Redirects the user to the referring url if on our servers
					 or back to home otherwise with an appropriate message">
		<!--- Note: The logic of the referer is handled by cfwheels. --->
		<cfset redirectTemplate = isDefined("application.accessDeniedHandler") ? application.accessDeniedHandler : "/shared/accessdenied.cfm">
		<cfset renderPage(template="/shared/accessdenied.cfm")>
	</cffunction>

	<cffunction name="populatePermissions" description="Populates the session with the permissions for the given group.">
		<cfargument name="groupid" required="yes" type="integer" hint="The id of the group who's permissions will be loaded.">
	
		<!--- Make sure the user struct exists --->
		<cfif !StructKeyExists(session,"user")>
			<cfset session.user = StructNew()>
		</cfif>
		<!--- Get the permissions from the db --->
		<cfset session.user.group = StructNew()>
		<cfquery name="permissions" datasource="#get('datasourceName')#">
			SELECT grouppermissions.groupid, grouppermissions.permissionid, permissions.name
	    		FROM grouppermissions
			INNER JOIN permissions
	    		ON grouppermissions.permissionid = permissions.id
			WHERE groupid = <cfqueryparam value="#arguments.groupid#">
		 </cfquery>
		<!--- Assign the permissions to the session --->
	        <cfloop query="permissions">
	            <cfset session.user.group[permissions["name"][permissions.currentRow]] = true>
	        </cfloop>
	</cffunction>

	<cffunction name="clearPermissions"
		description="Clears the permissions from the session so the user is no longer authenticated.">
		<cfif isDefined("session.user.group")>
			<cfset StructDelete(session.user, "group")>
		</cfif>
	</cffunction>

	<cffunction name="$boolify" description="Simply forces coldfusion to return a real boolean.">
		<cfargument name="cfBoolean" required="true">
		<cfreturn cfBoolean ? true : false>
	</cffunction>

</cfcomponent>
