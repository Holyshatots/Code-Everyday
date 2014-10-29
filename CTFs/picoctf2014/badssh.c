#include <stdio.h>

void main()
{
	int x = check_password("AAAA");
}

static int frobcmp(const char *chk, const char *str) {
        int rc = 0;
        size_t len = strlen(str);
        char *s = xstrdup(str);
        memfrob(s, len);

        if (strcmp(chk, s) == 0) {
                rc = 1;
        }

        free(s);
        return rc;
}

int check_password(const char *password) {
        return frobcmp("CGCDSE_XGKIBCDOY^OKFCDMSE_XLFKMY", password);
}

