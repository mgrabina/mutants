package meli.webapp.dtos;

public class DnaDto {
    private String[] dnaCode;

    // Default constructor for Jersey
    public DnaDto(){}

    public String[] getDnaCode() {
        return dnaCode;
    }

    public void setDnaCode(String[] dnaCode) {
        this.dnaCode = dnaCode;
    }
}
