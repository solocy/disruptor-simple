package wang.lovem.www.common;

/**
 * 要处理的数据
 *
 */
public class DataEvent {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DataEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
