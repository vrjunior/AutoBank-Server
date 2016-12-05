package us.guihouse.autobank.other;

import us.guihouse.autobank.servlets.Context;

/**
 * Created by guilherme on 05/12/16.
 */
public class PageParser {
    public static Long getPage(Context context) {
        Long page = 1L;
        String pageStr = context.getRequest().getParameter("page");

        if (pageStr != null && !pageStr.isEmpty()) {
            page = Long.parseLong(pageStr);
        }

        if (page < 1) {
            page = 1L;
        }

        return page;
    }
}
