package bookescape.libro;

import java.sql.Date;

public class Libro {
  private String isbn13;
  private String titolo;
  private Integer numeroVoti;
  private Integer totaleVoti;
  private String img;
  private String descrizione;
  private Date dataPub;
  
  public Libro() {
  }

  public Libro(String isbn13, String titolo, Integer numeroVoti, Integer totaleVoti, String img, String descrizione,
      Date dataPub) {
    super();
    this.isbn13 = isbn13;
    this.titolo = titolo;
    this.numeroVoti = numeroVoti;
    this.totaleVoti = totaleVoti;
    this.img = img;
    this.descrizione = descrizione;
    this.dataPub = dataPub;
  }

  public String getIsbn13() {
    return isbn13;
  }

  public void setIsbn13(String isbn13) {
    this.isbn13 = isbn13;
  }

  public String getTitolo() {
    return titolo;
  }

  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  public Integer getNumeroVoti() {
    return numeroVoti;
  }

  public void setNumeroVoti(Integer numeroVoti) {
    this.numeroVoti = numeroVoti;
  }

  public Integer getTotaleVoti() {
    return totaleVoti;
  }

  public void setTotaleVoti(Integer totaleVoti) {
    this.totaleVoti = totaleVoti;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Date getDataPub() {
    return dataPub;
  }

  public void setDataPub(Date dataPub) {
    this.dataPub = dataPub;
  }
  
}
