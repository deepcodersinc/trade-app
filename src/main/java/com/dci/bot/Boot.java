package com.dci.bot;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.model.Position;
import com.dci.bot.ws.FeedManager;
import com.dci.bot.ws.WSConnector;

public class Boot {

	public static void main(String[] args) {

		Options options = new Options();
		options.addOption("productId", true, "Id of the product to open a position on");
		options.addOption("buyPrice", true, "The price at which the position will be open");
		options.addOption("upperSellPrice", true,
				"The price you are willing to close a position and make a profit. Should be more than buy price.");
		options.addOption("lowerSellPrice", true,
				"The price you want are willing to close a position at and make a loss. Should be less than buy price.");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		if (args.length != 4) {
			formatter.printHelp("Trading App", options, true);
			System.exit(1);
		}

		try {
			CommandLine cmd = parser.parse(options, args);

			String productId = cmd.getOptionValue("productId");
			float buyPrice = Float.parseFloat(cmd.getOptionValue("buyPrice"));
			float upperSellPrice = Float.parseFloat(cmd.getOptionValue("upperSellPrice"));
			float lowerSellPrice = Float.parseFloat(cmd.getOptionValue("lowerSellPrice"));

			if (upperSellPrice <= buyPrice) {
				formatter.printHelp("upperSellPrice must be more than buy price", options, true);
				System.exit(1);
			}
			if (lowerSellPrice >= buyPrice) {
				formatter.printHelp("lowerSellPrice must be less than buy price", options, true);
				System.exit(1);
			}

			Position position = new Position(productId, buyPrice, upperSellPrice, lowerSellPrice);
			FeedManager feedManager = new FeedManager(new WSConnector().getConnection());

			feedManager.subscribe(position);

		} catch (ParseException e) {
			formatter.printHelp("Trading App", options, true);
			System.exit(1);
		} catch (NumberFormatException nfe) {
			formatter.printHelp("Enter floating point value", options, true);
		} catch (ApplicationException e) {
			formatter.printHelp(e.getMessage(), options, true);
		} catch (Exception e) {
			formatter.printHelp(e.getMessage(), options, true);
		}

	}

}
