package us.guihouse.autobank.other;

import us.guihouse.autobank.servlets.Context;

import java.util.List;

/**
 * Created by guilherme on 05/12/16.
 */
public class Pager<K> {
    private static final Long PER_PAGE = 15L;
    private static final Long PAGE_OPTIONS = 10L;

    private List<K> records;
    private Long perPage;
    private Long totalCount;
    private Long currentPage;

    public static <S> Pager<S> getPager(Context context) {
        Pager<S> pager = new Pager<S>(PageParser.getPage(context));
        pager.setPerPage(PER_PAGE);
        return pager;
    }

    public Pager(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Long getPerPage() {
        return perPage;
    }

    public List<K> getRecords() {
        return records;
    }

    public Long getTotalPages() {
        return totalCount / perPage;
    }

    public void setRecords(List<K> records) {
        this.records = records;
    }

    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getOffset() {
        return (currentPage - 1) * perPage;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public boolean canGoFirst() {
        return currentPage > 1;
    }

    public boolean canGoLast() {
        return currentPage * perPage < totalCount;
    }

    public boolean canGoNext() {
        return canGoLast();
    }

    public boolean canGoBack() {
        return canGoFirst();
    }

    public Long lastPage() {
        long extra = 0;

        if (totalCount % perPage != 0) {
            extra = 1;
        }

        return (totalCount / perPage) + extra;
    }

    public Long getNext() {
        return currentPage + 1;
    }

    public Long getPrevious() {
        return currentPage - 1;
    }

    public Long pageLoopStart() {
        Long start = currentPage - (PAGE_OPTIONS / 2);

        if (start < 1) {
            start = 1L;
        }

        return start;
    }

    public Long pageLoopEnd() {
        Long end = pageLoopStart() + PAGE_OPTIONS;

        if (end > getTotalPages()) {
            end = getTotalPages();
        }

        return end;
    }
}
