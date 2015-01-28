package muhammadsuhail.login;

import java.io.Serializable;

/**
 * Created by Muhammad Suhail on 1/13/2015.
 */
public class Wines implements Serializable{
    private String company_name;
    private String address;
    private String city;
    private String state;
    private String postal;
    private String country;
    private String phone;
    private String url;
    private String notes;
    private String status_;
    private String colour;
    private String username;

    public Wines(){
        this.company_name="";
        this.address="";
        this.city="";
        this.state="";
        this.postal="";
        this.country="";
        this.phone="";
        this.url="";
        this.notes="";
        this.status_="";
        this.colour="";
        this.username="";
    }

    public void set_company_name(String name)
    {
        company_name=name;
    }

    public String get_company_name()
    {
        return company_name;
    }

    public void set_address(String input)
    {
        address=input;
    }

    public String get_address()
    {
        return address;
    }

    public void set_city(String name)
    {
        city=name;
    }

    public String get_city()
    {
        return city;
    }

    public void set_state(String name)
    {
        state=name;
    }

    public String get_state()
    {
        return state;
    }

    public void set_postal(String name)
    {
        postal=name;
    }

    public String get_postal()
    {
        return postal;
    }

    public void set_country(String name)
    {
        country=name;
    }

    public String get_country()
    {
        return country;
    }

    public void set_phone(String name)
    {
        phone=name;
    }

    public String get_phone()
    {
        return phone;
    }

    public void set_url(String name)
    {
        url=name;
    }

    public String get_url()
    {
        return url;
    }

    public void set_notes(String name)
    {
        notes=name;
    }

    public String get_notes()
    {
        return notes;
    }


    public void set_status_(String name)
    {
        status_=name;
    }

    public String get_status_()
    {
        return status_;
    }

    public void set_colour(String name)
    {
        colour=name;
    }

    public String get_colour()
    {
        return colour;
    }

    public void set_username(String name)
    {
        username=name;
    }

    public String get_username()
    {
        return username;
    }




}
