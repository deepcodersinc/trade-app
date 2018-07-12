package com.dci.bot;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import com.dci.bot.http.RestTradeOrderClient;
import com.dci.bot.http.TradeOrderClient;
import com.dci.bot.model.Position;
import com.dci.bot.ws.WSTradeFeedManager;
import com.dci.bot.ws.listner.ConnectionStatusListner;
import com.dci.bot.ws.listner.TradeQuoteListner;
import com.dci.util.PropertyUtil;

public class AppBoot {

	public static void main(String[] args) {
		
		Options options = new Options();
		options.addOption("productId", true, "Id of the product to open a position on");
		options.addOption("buyPrice", true, "The price at which the position will be open");
        options.addOption("uSellPrice", true, "The price you are willing to close a position and make a profit");
        options.addOption("lSellPrice", true, "The price you want are willing to close a position at and make a loss");
				
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		
		try {
			cmd = parser.parse(options, args);
			
			if (args.length < 4) {
				new HelpFormatter().printHelp("Trading App", options, true);
				System.exit(1);
			}
			PropertyUtil.INSTANCE.setPropertyFile("environment.properties");
		
			Position position = new Position(
									cmd.getOptionValue("productId"),
									Float.parseFloat(cmd.getOptionValue("buyPrice")),
									Float.parseFloat(cmd.getOptionValue("uSellPrice")),
									Float.parseFloat(cmd.getOptionValue("lSellPrice")));
			
			TradeOrderClient orderManager = new RestTradeOrderClient();
			
			//Inject dependencies and create WebSocket connection
			WSTradeFeedManager.getWSConnection(new ConnectionStatusListner(), new TradeQuoteListner(orderManager));
			WSTradeFeedManager.subscribe(position);

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
