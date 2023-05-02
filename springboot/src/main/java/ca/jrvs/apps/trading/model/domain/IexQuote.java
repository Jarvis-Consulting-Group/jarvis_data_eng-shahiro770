package ca.jrvs.apps.trading.model.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * IEX Quote DTO between the MarketDataDao and  IEX Quote retrieved from the API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "avgTotalVolume",
    "calculationPrice",
    "change",
    "changePercent",
    "close",
    "closeSource",
    "closeTime",
    "companyName",
    "currency",
    "delayedPrice",
    "delayedPriceTime",
    "extendedChange",
    "extendedChangePercent",
    "extendedPrice",
    "extendedPriceTime",
    "high",
    "highSource",
    "highTime",
    "iexAskPrice",
    "iexAskSize",
    "iexBidPrice",
    "iexBidSize",
    "iexClose",
    "iexCloseTime",
    "iexLastUpdated",
    "iexMarketPercent",
    "iexOpen",
    "iexOpenTime",
    "iexRealtimePrice",
    "iexRealtimeSize",
    "iexVolume",
    "lastTradeTime",
    "latestPrice",
    "latestSource",
    "latestTime",
    "latestUpdate",
    "latestVolume",
    "low",
    "lowSource",
    "lowTime",
    "marketCap",
    "oddLotDelayedPrice",
    "oddLotDelayedPriceTime",
    "open",
    "openTime",
    "openSource",
    "peRatio",
    "previousClose",
    "previousVolume",
    "primaryExchange",
    "symbol",
    "volume",
    "week52High",
    "week52Low",
    "ytdChange",
    "isUSMarketOpen"
})
@Generated("jsonschema2pojo")
public class IexQuote {

    @JsonProperty("avgTotalVolume")
    private Object avgTotalVolume;
    @JsonProperty("calculationPrice")
    private String calculationPrice;
    @JsonProperty("change")
    private Object change;
    @JsonProperty("changePercent")
    private Object changePercent;
    @JsonProperty("close")
    private Object close;
    @JsonProperty("closeSource")
    private String closeSource;
    @JsonProperty("closeTime")
    private Object closeTime;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("delayedPrice")
    private Object delayedPrice;
    @JsonProperty("delayedPriceTime")
    private Object delayedPriceTime;
    @JsonProperty("extendedChange")
    private Object extendedChange;
    @JsonProperty("extendedChangePercent")
    private Object extendedChangePercent;
    @JsonProperty("extendedPrice")
    private Object extendedPrice;
    @JsonProperty("extendedPriceTime")
    private Object extendedPriceTime;
    @JsonProperty("high")
    private Object high;
    @JsonProperty("highSource")
    private String highSource;
    @JsonProperty("highTime")
    private Object highTime;
    @JsonProperty("iexAskPrice")
    private Object iexAskPrice;
    @JsonProperty("iexAskSize")
    private Object iexAskSize;
    @JsonProperty("iexBidPrice")
    private Object iexBidPrice;
    @JsonProperty("iexBidSize")
    private Object iexBidSize;
    @JsonProperty("iexClose")
    private Object iexClose;
    @JsonProperty("iexCloseTime")
    private Object iexCloseTime;
    @JsonProperty("iexLastUpdated")
    private Object iexLastUpdated;
    @JsonProperty("iexMarketPercent")
    private Object iexMarketPercent;
    @JsonProperty("iexOpen")
    private Object iexOpen;
    @JsonProperty("iexOpenTime")
    private Object iexOpenTime;
    @JsonProperty("iexRealtimePrice")
    private Object iexRealtimePrice;
    @JsonProperty("iexRealtimeSize")
    private Object iexRealtimeSize;
    @JsonProperty("iexVolume")
    private Object iexVolume;
    @JsonProperty("lastTradeTime")
    private Object lastTradeTime;
    @JsonProperty("latestPrice")
    private Object latestPrice;
    @JsonProperty("latestSource")
    private String latestSource;
    @JsonProperty("latestTime")
    private String latestTime;
    @JsonProperty("latestUpdate")
    private Object latestUpdate;
    @JsonProperty("latestVolume")
    private Object latestVolume;
    @JsonProperty("low")
    private Object low;
    @JsonProperty("lowSource")
    private String lowSource;
    @JsonProperty("lowTime")
    private Object lowTime;
    @JsonProperty("marketCap")
    private Object marketCap;
    @JsonProperty("oddLotDelayedPrice")
    private Object oddLotDelayedPrice;
    @JsonProperty("oddLotDelayedPriceTime")
    private Object oddLotDelayedPriceTime;
    @JsonProperty("open")
    private Object open;
    @JsonProperty("openTime")
    private Object openTime;
    @JsonProperty("openSource")
    private String openSource;
    @JsonProperty("peRatio")
    private Object peRatio;
    @JsonProperty("previousClose")
    private Object previousClose;
    @JsonProperty("previousVolume")
    private Object previousVolume;
    @JsonProperty("primaryExchange")
    private String primaryExchange;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("volume")
    private Object volume;
    @JsonProperty("week52High")
    private Object week52High;
    @JsonProperty("week52Low")
    private Object week52Low;
    @JsonProperty("ytdChange")
    private Object ytdChange;
    @JsonProperty("isUSMarketOpen")
    private Boolean isUSMarketOpen;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("avgTotalVolume")
    public Object getAvgTotalVolume() {
        return avgTotalVolume;
    }

    @JsonProperty("avgTotalVolume")
    public void setAvgTotalVolume(Object avgTotalVolume) {
        this.avgTotalVolume = avgTotalVolume;
    }

    @JsonProperty("calculationPrice")
    public String getCalculationPrice() {
        return calculationPrice;
    }

    @JsonProperty("calculationPrice")
    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    @JsonProperty("change")
    public Object getChange() {
        return change;
    }

    @JsonProperty("change")
    public void setChange(Object change) {
        this.change = change;
    }

    @JsonProperty("changePercent")
    public Object getChangePercent() {
        return changePercent;
    }

    @JsonProperty("changePercent")
    public void setChangePercent(Object changePercent) {
        this.changePercent = changePercent;
    }

    @JsonProperty("close")
    public Object getClose() {
        return close;
    }

    @JsonProperty("close")
    public void setClose(Object close) {
        this.close = close;
    }

    @JsonProperty("closeSource")
    public String getCloseSource() {
        return closeSource;
    }

    @JsonProperty("closeSource")
    public void setCloseSource(String closeSource) {
        this.closeSource = closeSource;
    }

    @JsonProperty("closeTime")
    public Object getCloseTime() {
        return closeTime;
    }

    @JsonProperty("closeTime")
    public void setCloseTime(Object closeTime) {
        this.closeTime = closeTime;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("delayedPrice")
    public Object getDelayedPrice() {
        return delayedPrice;
    }

    @JsonProperty("delayedPrice")
    public void setDelayedPrice(Object delayedPrice) {
        this.delayedPrice = delayedPrice;
    }

    @JsonProperty("delayedPriceTime")
    public Object getDelayedPriceTime() {
        return delayedPriceTime;
    }

    @JsonProperty("delayedPriceTime")
    public void setDelayedPriceTime(Object delayedPriceTime) {
        this.delayedPriceTime = delayedPriceTime;
    }

    @JsonProperty("extendedChange")
    public Object getExtendedChange() {
        return extendedChange;
    }

    @JsonProperty("extendedChange")
    public void setExtendedChange(Object extendedChange) {
        this.extendedChange = extendedChange;
    }

    @JsonProperty("extendedChangePercent")
    public Object getExtendedChangePercent() {
        return extendedChangePercent;
    }

    @JsonProperty("extendedChangePercent")
    public void setExtendedChangePercent(Object extendedChangePercent) {
        this.extendedChangePercent = extendedChangePercent;
    }

    @JsonProperty("extendedPrice")
    public Object getExtendedPrice() {
        return extendedPrice;
    }

    @JsonProperty("extendedPrice")
    public void setExtendedPrice(Object extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    @JsonProperty("extendedPriceTime")
    public Object getExtendedPriceTime() {
        return extendedPriceTime;
    }

    @JsonProperty("extendedPriceTime")
    public void setExtendedPriceTime(Object extendedPriceTime) {
        this.extendedPriceTime = extendedPriceTime;
    }

    @JsonProperty("high")
    public Object getHigh() {
        return high;
    }

    @JsonProperty("high")
    public void setHigh(Object high) {
        this.high = high;
    }

    @JsonProperty("highSource")
    public String getHighSource() {
        return highSource;
    }

    @JsonProperty("highSource")
    public void setHighSource(String highSource) {
        this.highSource = highSource;
    }

    @JsonProperty("highTime")
    public Object getHighTime() {
        return highTime;
    }

    @JsonProperty("highTime")
    public void setHighTime(Object highTime) {
        this.highTime = highTime;
    }

    @JsonProperty("iexAskPrice")
    public Object getIexAskPrice() {
        return iexAskPrice;
    }

    @JsonProperty("iexAskPrice")
    public void setIexAskPrice(Object iexAskPrice) {
        this.iexAskPrice = iexAskPrice;
    }

    @JsonProperty("iexAskSize")
    public Object getIexAskSize() {
        return iexAskSize;
    }

    @JsonProperty("iexAskSize")
    public void setIexAskSize(Object iexAskSize) {
        this.iexAskSize = iexAskSize;
    }

    @JsonProperty("iexBidPrice")
    public Object getIexBidPrice() {
        return iexBidPrice;
    }

    @JsonProperty("iexBidPrice")
    public void setIexBidPrice(Object iexBidPrice) {
        this.iexBidPrice = iexBidPrice;
    }

    @JsonProperty("iexBidSize")
    public Object getIexBidSize() {
        return iexBidSize;
    }

    @JsonProperty("iexBidSize")
    public void setIexBidSize(Object iexBidSize) {
        this.iexBidSize = iexBidSize;
    }

    @JsonProperty("iexClose")
    public Object getIexClose() {
        return iexClose;
    }

    @JsonProperty("iexClose")
    public void setIexClose(Object iexClose) {
        this.iexClose = iexClose;
    }

    @JsonProperty("iexCloseTime")
    public Object getIexCloseTime() {
        return iexCloseTime;
    }

    @JsonProperty("iexCloseTime")
    public void setIexCloseTime(Object iexCloseTime) {
        this.iexCloseTime = iexCloseTime;
    }

    @JsonProperty("iexLastUpdated")
    public Object getIexLastUpdated() {
        return iexLastUpdated;
    }

    @JsonProperty("iexLastUpdated")
    public void setIexLastUpdated(Object iexLastUpdated) {
        this.iexLastUpdated = iexLastUpdated;
    }

    @JsonProperty("iexMarketPercent")
    public Object getIexMarketPercent() {
        return iexMarketPercent;
    }

    @JsonProperty("iexMarketPercent")
    public void setIexMarketPercent(Object iexMarketPercent) {
        this.iexMarketPercent = iexMarketPercent;
    }

    @JsonProperty("iexOpen")
    public Object getIexOpen() {
        return iexOpen;
    }

    @JsonProperty("iexOpen")
    public void setIexOpen(Object iexOpen) {
        this.iexOpen = iexOpen;
    }

    @JsonProperty("iexOpenTime")
    public Object getIexOpenTime() {
        return iexOpenTime;
    }

    @JsonProperty("iexOpenTime")
    public void setIexOpenTime(Object iexOpenTime) {
        this.iexOpenTime = iexOpenTime;
    }

    @JsonProperty("iexRealtimePrice")
    public Object getIexRealtimePrice() {
        return iexRealtimePrice;
    }

    @JsonProperty("iexRealtimePrice")
    public void setIexRealtimePrice(Object iexRealtimePrice) {
        this.iexRealtimePrice = iexRealtimePrice;
    }

    @JsonProperty("iexRealtimeSize")
    public Object getIexRealtimeSize() {
        return iexRealtimeSize;
    }

    @JsonProperty("iexRealtimeSize")
    public void setIexRealtimeSize(Object iexRealtimeSize) {
        this.iexRealtimeSize = iexRealtimeSize;
    }

    @JsonProperty("iexVolume")
    public Object getIexVolume() {
        return iexVolume;
    }

    @JsonProperty("iexVolume")
    public void setIexVolume(Object iexVolume) {
        this.iexVolume = iexVolume;
    }

    @JsonProperty("lastTradeTime")
    public Object getLastTradeTime() {
        return lastTradeTime;
    }

    @JsonProperty("lastTradeTime")
    public void setLastTradeTime(Object lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    @JsonProperty("latestPrice")
    public Object getLatestPrice() {
        return latestPrice;
    }

    @JsonProperty("latestPrice")
    public void setLatestPrice(Object latestPrice) {
        this.latestPrice = latestPrice;
    }

    @JsonProperty("latestSource")
    public String getLatestSource() {
        return latestSource;
    }

    @JsonProperty("latestSource")
    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    @JsonProperty("latestTime")
    public String getLatestTime() {
        return latestTime;
    }

    @JsonProperty("latestTime")
    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    @JsonProperty("latestUpdate")
    public Object getLatestUpdate() {
        return latestUpdate;
    }

    @JsonProperty("latestUpdate")
    public void setLatestUpdate(Object latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    @JsonProperty("latestVolume")
    public Object getLatestVolume() {
        return latestVolume;
    }

    @JsonProperty("latestVolume")
    public void setLatestVolume(Object latestVolume) {
        this.latestVolume = latestVolume;
    }

    @JsonProperty("low")
    public Object getLow() {
        return low;
    }

    @JsonProperty("low")
    public void setLow(Object low) {
        this.low = low;
    }

    @JsonProperty("lowSource")
    public String getLowSource() {
        return lowSource;
    }

    @JsonProperty("lowSource")
    public void setLowSource(String lowSource) {
        this.lowSource = lowSource;
    }

    @JsonProperty("lowTime")
    public Object getLowTime() {
        return lowTime;
    }

    @JsonProperty("lowTime")
    public void setLowTime(Object lowTime) {
        this.lowTime = lowTime;
    }

    @JsonProperty("marketCap")
    public Object getMarketCap() {
        return marketCap;
    }

    @JsonProperty("marketCap")
    public void setMarketCap(Object marketCap) {
        this.marketCap = marketCap;
    }

    @JsonProperty("oddLotDelayedPrice")
    public Object getOddLotDelayedPrice() {
        return oddLotDelayedPrice;
    }

    @JsonProperty("oddLotDelayedPrice")
    public void setOddLotDelayedPrice(Object oddLotDelayedPrice) {
        this.oddLotDelayedPrice = oddLotDelayedPrice;
    }

    @JsonProperty("oddLotDelayedPriceTime")
    public Object getOddLotDelayedPriceTime() {
        return oddLotDelayedPriceTime;
    }

    @JsonProperty("oddLotDelayedPriceTime")
    public void setOddLotDelayedPriceTime(Object oddLotDelayedPriceTime) {
        this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
    }

    @JsonProperty("open")
    public Object getOpen() {
        return open;
    }

    @JsonProperty("open")
    public void setOpen(Object open) {
        this.open = open;
    }

    @JsonProperty("openTime")
    public Object getOpenTime() {
        return openTime;
    }

    @JsonProperty("openTime")
    public void setOpenTime(Object openTime) {
        this.openTime = openTime;
    }

    @JsonProperty("openSource")
    public String getOpenSource() {
        return openSource;
    }

    @JsonProperty("openSource")
    public void setOpenSource(String openSource) {
        this.openSource = openSource;
    }

    @JsonProperty("peRatio")
    public Object getPeRatio() {
        return peRatio;
    }

    @JsonProperty("peRatio")
    public void setPeRatio(Object peRatio) {
        this.peRatio = peRatio;
    }

    @JsonProperty("previousClose")
    public Object getPreviousClose() {
        return previousClose;
    }

    @JsonProperty("previousClose")
    public void setPreviousClose(Object previousClose) {
        this.previousClose = previousClose;
    }

    @JsonProperty("previousVolume")
    public Object getPreviousVolume() {
        return previousVolume;
    }

    @JsonProperty("previousVolume")
    public void setPreviousVolume(Object previousVolume) {
        this.previousVolume = previousVolume;
    }

    @JsonProperty("primaryExchange")
    public String getPrimaryExchange() {
        return primaryExchange;
    }

    @JsonProperty("primaryExchange")
    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("volume")
    public Object getVolume() {
        return volume;
    }

    @JsonProperty("volume")
    public void setVolume(Object volume) {
        this.volume = volume;
    }

    @JsonProperty("week52High")
    public Object getWeek52High() {
        return week52High;
    }

    @JsonProperty("week52High")
    public void setWeek52High(Object week52High) {
        this.week52High = week52High;
    }

    @JsonProperty("week52Low")
    public Object getWeek52Low() {
        return week52Low;
    }

    @JsonProperty("week52Low")
    public void setWeek52Low(Object week52Low) {
        this.week52Low = week52Low;
    }

    @JsonProperty("ytdChange")
    public Object getYtdChange() {
        return ytdChange;
    }

    @JsonProperty("ytdChange")
    public void setYtdChange(Object ytdChange) {
        this.ytdChange = ytdChange;
    }

    @JsonProperty("isUSMarketOpen")
    public Boolean getIsUSMarketOpen() {
        return isUSMarketOpen;
    }

    @JsonProperty("isUSMarketOpen")
    public void setIsUSMarketOpen(Boolean isUSMarketOpen) {
        this.isUSMarketOpen = isUSMarketOpen;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

