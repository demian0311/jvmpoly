package javafunctional.java8.fpij;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

class Asset {
    public enum AssetType {BOND, STOCK}

    private final AssetType assetType;
    private final int value;

    public Asset(
            final AssetType assetTypeIn,
            final int valueIn){
        assetType = assetTypeIn;
        value = valueIn;
    }

    public AssetType getAssetType() { return assetType; }
    public int getValue(){ return value; }
}

class FluentMailer{
    private FluentMailer(){}

    public FluentMailer from(final String address){ return this;}
    public FluentMailer to(final String address){ return this;}
    public FluentMailer subject(final String address){ return this;}
    public FluentMailer body(final String address){ return this;}

    public static void send(final Consumer<FluentMailer> block){
        final FluentMailer mailer = new FluentMailer();
        block.accept(mailer);
        System.out.println("mailing %s");
    }
}

public class Chapter4 {

    final List<Asset> assets = Arrays.asList(
            new Asset(Asset.AssetType.BOND, 1000),
            new Asset(Asset.AssetType.BOND, 2000),
            new Asset(Asset.AssetType.STOCK, 3000),
            new Asset(Asset.AssetType.STOCK, 4000)
    );

    @Test public void testTotalAssetValues() {
        assertEquals(10_000, assets.stream().mapToInt(a -> a.getValue()).sum());
    }

    @Test public void withFilter(){
        final int totalBonds = assets.stream()
                .filter(a -> a.getAssetType().equals(Asset.AssetType.BOND))
                .mapToInt(a -> a.getValue())
                .sum();
        assertEquals(3_000, totalBonds);
    }

    /**
     * Nice way using lambdas to sex up your APIs.
     *
     * This is cool, you get IDE help, strongly typed, good stuff.
     */
    @Test public void fluentMailer(){
        FluentMailer.send(mailer -> mailer
                .from("demian0311@gmail.com")
                .to("demian.neidetcher@twcable.com")
                .subject("hello world")
                .body("is this thing on?"));
    }
}
