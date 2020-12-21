package orm;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.InetAddress;

@Entity
@Table(name = "ari_settings")
public class AriSettings extends PanacheEntity {
    @Column(nullable = false, name = "server_IP")
    public InetAddress ariIP;

    @Column(nullable = false, name = "user")
    public String user;

    @Column(nullable = false, name = "password")
    public String password;

    @Column(nullable = false, name = "ari_version")
    public String ariVersion;

    @Column(nullable = false, name = "application_name")
    public String applicationName;
}
