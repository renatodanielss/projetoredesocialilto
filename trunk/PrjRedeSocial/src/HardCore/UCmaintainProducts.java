package HardCore;

import java.io.*;
import java.io.File;
import javax.servlet.*;
import javax.servlet.jsp.*;

public class UCmaintainProducts {
	private Text text = new Text();



	public UCmaintainProducts() {
	}



	public UCmaintainProducts(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public Content getExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		Content products = new Content(text);
		return products;
	}



	public Content getImport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		Content products = new Content(text);
		return products;
	}



	public Content doExport(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return new Content(text);
		Content products = new Content(text);
		String SQL = "select * from content where contentclass='product' order by title, version, id";
		products.records("", "", "", "", "", "", "", db, myconfig, SQL);
		myresponse.setHeader("Content-Disposition", "filename=products.csv");
		myresponse.setContentType("x-application/csv");
		return products;
	}



	public String doImport(JspWriter out, ServletContext server, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = RequireUser.SuperAdministrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		if (! accesspermission) return "";
		String output = "";
		String save_content_editor = myconfig.get(db, "content_editor");
		Fileupload filepost = new Fileupload(myrequest, DOCUMENT_ROOT + myconfig.get(db, "URLrootpath"), myconfig.get(db, "URLuploadpath"));
		myconfig.setTemp("content_editor", save_content_editor);
		boolean update_stock_amounts = (! filepost.getParameter("update_stock_amounts").equals(""));
		if (! filepost.getParameter("file.server_filename").equals("")) {
			String filename = Common.getRealPath(server, myconfig.get(db, "URLrootpath") + filepost.getParameter("file.server_filename"));
			File fh = new File(filename);
			if (fh.exists()) {
				BufferedReader input = null;
				try {
					input = new BufferedReader(new FileReader(filename));
					String my_line = "" + input.readLine();
					if (my_line.equals("id,group,type,version,title,brand,colour,size,weight,width,height,depth,volume,info,options,code,location,currency,price,period,cost,stock,stocktext,lowstock,lowstocktext,restocked,nostocktext,prebackorder,author,description,keywords,metainfo")) {
						if (out != null) out.print(text.display("products.import.importing"));
						output += text.display("products.import.importing");
						while ((my_line = input.readLine()) != null) {
							String my_nextline;
							int quotes = 0;
							for (int i=0; i<my_line.length(); i++) {
								if (my_line.charAt(i) == '"') {
									quotes++;
								}
								if ((i == my_line.length()-1) && ((quotes % 2) == 1) && ((my_nextline = input.readLine()) != null)) {
									my_line += "\r\n" + my_nextline;
								}
							}
							if (out != null) out.print(" . ");
							if (out != null) out.flush();
							output += " . ";
							if (! my_line.equals("")) {
								String[] my_values = (my_line+",,,,,,,,,,,,,,,,,,,,,x").split(",");
								for (int i=0; i<my_values.length; i++) {
									if (my_values[i].startsWith("\"") && (my_values[i].endsWith("\""))) {
										my_values[i] = "" + my_values[i].substring(1, my_values[i].length()-1).replaceAll("\"\"", "\"");
									} else if (my_values[i].startsWith("\"") && (! my_values[i].endsWith("\""))) {
										String myvalue = "" + my_values[i];
										boolean joinvalues = true;
										int k=i+1;
										for (int j=i+1; j<my_values.length; j++) {
											if (joinvalues) {
												myvalue += "," + my_values[j];
											} else {
												my_values[k++] = "" + my_values[j];
												my_values[j] = "";
											}
											if (my_values[j].endsWith("\"")) {
												joinvalues = false;
											}
										}
										my_values[i] = "" + myvalue.substring(1, myvalue.length()-1).replaceAll("\"\"", "\"");
									}
								}
								String id = my_values[0];
								String contentgroup = my_values[1];
								String contenttype = my_values[2];
								String version = my_values[3];
								String title = my_values[4];
								String product_brand = my_values[5];
								String product_colour = my_values[6];
								String product_size = my_values[7];
								String product_weight = my_values[8];
								String product_width = my_values[9];
								String product_height = my_values[10];
								String product_depth = my_values[11];
								String product_volume = my_values[12];
								String product_info = my_values[13];
								String product_options = my_values[14];
								String product_code = my_values[15];
								String product_location = my_values[16];
								String product_currency = my_values[17];
								String product_price = my_values[18];
								String product_period = my_values[19];
								String product_cost = my_values[20];
								String product_stock = my_values[21];
								String product_stocktext = my_values[22];
								String product_lowstock = my_values[23];
								String product_lowstocktext = my_values[24];
								String product_restocked = my_values[25];
								String product_nostocktext = my_values[26];
								String product_nostock_order = my_values[27];
								String author = my_values[28];
								String description = my_values[29];
								String keywords = my_values[30];
								String metainfo = my_values[31];
								Content product = new Content(text);
								product.preview_read(db, myconfig, id);
								if (updated_product(db, product, id, contentgroup, contenttype, version, title, product_brand, product_colour, product_size, product_weight, product_width, product_height, product_depth, product_volume, product_info, product_options, product_code, product_location, product_currency, product_price, product_period, product_cost, product_stock, product_stocktext, product_lowstock, product_lowstocktext, product_restocked, product_nostocktext, product_nostock_order, author, description, keywords, metainfo, update_stock_amounts)) {
									product.update(db, myconfig, id, "content", "id");
								}
								product = new Content(text);
								product.public_read(db, myconfig, id);
								if (updated_product(db, product, id, contentgroup, contenttype, version, title, product_brand, product_colour, product_size, product_weight, product_width, product_height, product_depth, product_volume, product_info, product_options, product_code, product_location, product_currency, product_price, product_period, product_cost, product_stock, product_stocktext, product_lowstock, product_lowstocktext, product_restocked, product_nostocktext, product_nostock_order, author, description, keywords, metainfo, update_stock_amounts)) {
									product.update(db, myconfig, id, "content_public", "id");
								}
							}
						}
						if (out != null) out.print(text.display("error.products.import.done"));
						output += text.display("error.products.import.done");
					} else {
						if (out != null) out.print(text.display("error.products.import.format"));
						output += text.display("error.products.import.format");
					}
					input.close();
				} catch (FileNotFoundException e) {
					if (input != null) try { input.close(); } catch (IOException ee) { ; }
				} catch (IOException e) {
					if (input != null) try { input.close(); } catch (IOException ee) { ; }
				}
				fh.delete();
			}
		}
		return output;
	}



	private boolean updated_product(DB db, Content product, String id, String contentgroup, String contenttype, String version, String title, String product_brand, String product_colour, String product_size, String product_weight, String product_width, String product_height, String product_depth, String product_volume, String product_info, String product_options, String product_code, String product_location, String product_currency, String product_price, String product_period, String product_cost, String product_stock, String product_stocktext, String product_lowstock, String product_lowstocktext, String product_restocked, String product_nostocktext, String product_nostock_order, String author, String description, String keywords, String metainfo, boolean update_stock_amounts) {
		boolean updated = false;
		if (product.getId().equals(id)) {
			if (! product.getContentGroup().equals(contentgroup)) {
				product.setContentGroup(contentgroup);
				updated = true;
			}
			if (! product.getContentType().equals(contenttype)) {
				product.setContentType(contenttype);
				updated = true;
			}
			if (! product.getVersion().equals(version)) {
				product.setVersion(version);
				updated = true;
			}
			if (! product.getTitle().equals(title)) {
				product.setTitle(title);
				updated = true;
			}
			if (! product.getProductBrand().equals(product_brand)) {
				product.setProductBrand(product_brand);
				updated = true;
			}
			if (! product.getProductColour().equals(product_colour)) {
				product.setProductColour(product_colour);
				updated = true;
			}
			if (! product.getProductSize().equals(product_size)) {
				product.setProductSize(product_size);
				updated = true;
			}
			if (! product.getProductWeight().equals(product_weight)) {
				product.setProductWeight(product_weight);
				updated = true;
			}
			if (! product.getProductWidth().equals(product_width)) {
				product.setProductWidth(product_width);
				updated = true;
			}
			if (! product.getProductHeight().equals(product_height)) {
				product.setProductHeight(product_height);
				updated = true;
			}
			if (! product.getProductDepth().equals(product_depth)) {
				product.setProductDepth(product_depth);
				updated = true;
			}
			if (! product.getProductVolume().equals(product_volume)) {
				product.setProductVolume(product_volume);
				updated = true;
			}
			if (! product.getProductInfo().equals(product_info)) {
				product.setProductInfo(product_info);
				updated = true;
			}
			if (! product.getProductOptions().equals(product_options)) {
				product.setProductOptions(product_options);
				updated = true;
			}
			if (! product.getProductCode().equals(product_code)) {
				product.setProductCode(product_code);
				updated = true;
			}
			if (! product.getProductLocation().equals(product_location)) {
				product.setProductLocation(product_location);
				updated = true;
			}
			if (! product.getProductCurrency().equals(product_currency)) {
				product.setProductCurrency(product_currency);
				updated = true;
			}
			if (! product.getProductPrice().equals(product_price)) {
				product.setProductPrice(product_price);
				updated = true;
			}
			if (! product.getProductPeriod().equals(product_period)) {
				product.setProductPeriod(product_period);
				updated = true;
			}
			if (! product.getProductCost().equals(product_cost)) {
				product.setProductCost(product_cost);
				updated = true;
			}
			if (update_stock_amounts && (! product.getProductStockAmount(db).equals(product_stock))) {
				product.setProductStockAmount(db, product_stock);
				updated = true;
			}
			if (! product.getProductStockText().equals(product_stocktext)) {
				product.setProductStockText(product_stocktext);
				updated = true;
			}
			if (! product.getProductLowStockAmount().equals(product_lowstock)) {
				product.setProductLowStockAmount(product_lowstock);
				updated = true;
			}
			if (! product.getProductLowStockText().equals(product_lowstocktext)) {
				product.setProductLowStockText(product_lowstocktext);
				updated = true;
			}
			if (update_stock_amounts && (! product.getProductRestockedAmount(db).equals(product_restocked))) {
				product.setProductRestockedAmount(db, product_restocked);
				updated = true;
			}
			if (! product.getProductNoStockText().equals(product_nostocktext)) {
				product.setProductNoStockText(product_nostocktext);
				updated = true;
			}
			if (! product.getProductNoStockOrder().equals(product_nostock_order)) {
				product.setProductNoStockOrder(product_nostock_order);
				updated = true;
			}
			if (! product.getAuthor().equals(author)) {
				product.setAuthor(author);
				updated = true;
			}
			if (! product.getDescription().equals(description)) {
				product.setDescription(description);
				updated = true;
			}
			if (! product.getKeywords().equals(keywords)) {
				product.setKeywords(keywords);
				updated = true;
			}
			if (! product.getMetaInfo().equals(metainfo)) {
				product.setMetaInfo(metainfo);
				updated = true;
			}
		}
		return updated;
	}



}
