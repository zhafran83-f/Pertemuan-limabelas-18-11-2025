/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
@Entity
@Table(name = "login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login_1.findAll", query = "SELECT l FROM Login_1 l"),
    @NamedQuery(name = "Login_1.findByUsername", query = "SELECT l FROM Login_1 l WHERE l.username = :username"),
    @NamedQuery(name = "Login_1.findByPw", query = "SELECT l FROM Login_1 l WHERE l.pw = :pw"),
    @NamedQuery(name = "Login_1.findByPertanyaan", query = "SELECT l FROM Login_1 l WHERE l.pertanyaan = :pertanyaan"),
    @NamedQuery(name = "Login_1.findByJawaban", query = "SELECT l FROM Login_1 l WHERE l.jawaban = :jawaban")})
public class Login_1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "pw")
    private String pw;
    @Basic(optional = false)
    @Column(name = "pertanyaan")
    private String pertanyaan;
    @Basic(optional = false)
    @Column(name = "jawaban")
    private String jawaban;

    public Login_1() {
    }

    public Login_1(String username) {
        this.username = username;
    }

    public Login_1(String username, String pw, String pertanyaan, String jawaban) {
        this.username = username;
        this.pw = pw;
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login_1)) {
            return false;
        }
        Login_1 other = (Login_1) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pertemuan_15.Login_1[ username=" + username + " ]";
    }
    
}
