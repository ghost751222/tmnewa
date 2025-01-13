package vo;


import lombok.Data;

@Data
public class RequestQueryVo {

    private String account;
    private String sort;
    private int pageIndex;
    private int pageSize;

}
