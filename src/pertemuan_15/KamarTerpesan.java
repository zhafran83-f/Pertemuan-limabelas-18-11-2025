/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
@Entity
@Table(name = "kamar_terpesan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KamarTerpesan.findAll", query = "SELECT k FROM KamarTerpesan k"),
    @NamedQuery(name = "KamarTerpesan.findByIdKamarTerpesan", query = "SELECT k FROM KamarTerpesan k WHERE k.idKamarTerpesan = :idKamarTerpesan"),
    @NamedQuery(name = "KamarTerpesan.findByBookedAt", query = "SELECT k FROM KamarTerpesan k WHERE k.bookedAt = :bookedAt")})
public class KamarTerpesan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_kamar_terpesan")
    private Integer idKamarTerpesan;
    @Column(name = "booked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookedAt;
    @JoinColumn(name = "id_kamar", referencedColumnName = "id_kamar")
    @ManyToOne(optional = false)
    private Kamar idKamar;
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    @ManyToOne(optional = false)
    private Tamu nik;

    public KamarTerpesan() {
    }

    public KamarTerpesan(Integer idKamarTerpesan) {
        this.idKamarTerpesan = idKamarTerpesan;
    }

    public Integer getIdKamarTerpesan() {
        return idKamarTerpesan;
    }

    public void setIdKamarTerpesan(Integer idKamarTerpesan) {
        this.idKamarTerpesan = idKamarTerpesan;
    }

    public Date getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Date bookedAt) {
        this.bookedAt = bookedAt;
    }

    public Kamar getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(Kamar idKamar) {
        this.idKamar = idKamar;
    }

    public Tamu getNik() {
        return nik;
    }

    public void setNik(Tamu nik) {
        this.nik = nik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKamarTerpesan != null ? idKamarTerpesan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KamarTerpesan)) {
            return false;
        }
        KamarTerpesan other = (KamarTerpesan) object;
        if ((this.idKamarTerpesan == null && other.idKamarTerpesan != null) || (this.idKamarTerpesan != null && !this.idKamarTerpesan.equals(other.idKamarTerpesan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pertemuan_15.KamarTerpesan[ idKamarTerpesan=" + idKamarTerpesan + " ]";
    }
    
}
