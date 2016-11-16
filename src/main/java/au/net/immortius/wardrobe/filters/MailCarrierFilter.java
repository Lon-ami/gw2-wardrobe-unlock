package au.net.immortius.wardrobe.filters;

import au.net.immortius.wardrobe.entities.Data;

import java.util.function.Predicate;

public class MailCarrierFilter implements Predicate<Data> {
    @Override
    public boolean test(Data data) {
        return data.name.contains(" Mail ");
    }
}
