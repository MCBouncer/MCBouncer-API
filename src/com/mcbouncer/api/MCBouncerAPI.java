package com.mcbouncer.api;

import com.mcbouncer.exception.APIException;
import com.mcbouncer.exception.NetworkException;
import com.mcbouncer.exception.ParserException;
import com.mcbouncer.http.Transport;
import com.mcbouncer.http.Request;
import com.mcbouncer.http.Response;
import com.mcbouncer.util.HTTPUtils;
import com.mcbouncer.util.MiscUtils;
import com.mcbouncer.util.node.JSONNode;
import com.mcbouncer.util.node.MapNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to interact with the MCBouncer website
 * itself. Each method throws NetworkException and
 * APIException. 
 * 
 * APIException is thrown when there is a problem 
 * parsing the API result. NetworkException is thrown 
 * when there is a problem contacting the website.
 * 
 * All URLs that contain an API key are obfuscated with
 * /apiUNIQKey/ until they get sent to getURL(). This is 
 * to prevent debug messages unintentionally revealing
 * a server's API key. This also prevents stack traces
 * revealing the key.
 * 
 */
public class MCBouncerAPI {
    protected String lastError = "";

    public MCBouncerAPI() {
    }

    /**
     * Gets a MCBouncer API url with the given parameters.
     * 
     * This method is a varargs method, so in order to send 
     * a request to http://mcb.com/api/foo/Bar/baz, one would 
     * call the method as: getAPIURL("foo", "Bar", "baz")
     * 
     * @param APIKey API Key to use
     * @param params The parameters to send to the API.
     * @return
     * @throws NetworkException 
     */
    protected Response getAPIURL(String host, String APIKey, String... params) throws NetworkException {
        String[] newParams = new String[params.length];
        int i = 0;
        for (String param : params) {
            param = stripColor(param);
            newParams[i] = HTTPUtils.urlEncode(param);
            i++;
        }

        String url = host + "/api/" + MiscUtils.join(newParams, "/"); //Appends the website

        url = url.replaceAll("apiUNIQKey", APIKey); //Replaces apiUNIQKey with the real key

        Transport transport = new Transport();
        Request request = new Request();
        request.setURL(url);
        transport.setRequest(request);

        return transport.sendURL();
    }

    /**
     * Gets the count of something. Type can be one of "Ban", "IPBan", or "Note"
     * 
     * @param APIKey API Key to use
     * @param type
     * @param user
     * @return
     * @throws NetworkException
     * @throws APIException 
     */
    protected Integer getTypeCount(String host, String APIKey, String type, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL(host, APIKey, "get" + type + "Count", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return json.getInteger("totalcount", 0);
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Returns the number of bans a user has on every server
     *
     * @param APIKey API Key to use
     * @param user Username to check
     * @return Integer Number of bans
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getBanCount(String host, String APIKey, String user) throws NetworkException, APIException {
        return getTypeCount(host, APIKey, "Ban", user);
    }

    /**
     * Returns the number of bans an IP has on every server
     * 
     * @param APIKey API Key to use
     * @param user IP to check
     * @return Integer Number of bans
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getIPBanCount(String host, String APIKey, String user) throws NetworkException, APIException {
        return getTypeCount(host, APIKey, "IPBan", user);
    }

    /**
     * Returns the number of bans a user has on every server,
     * in addition to the number of bans the IP has on every
     * server. Used to get a total ban count.
     * 
     * @param APIKey API Key to use
     * @param user Username to check
     * @param ip IP address to check
     * @return Integer Number of banss
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getTotalBanCount(String host, String APIKey, String user, String ip) throws NetworkException, APIException {
        return getTypeCount(host, APIKey, "Ban", user) + getTypeCount(host, APIKey, "IPBan", ip);
    }

    /**
     * Returns the number of notes a user has on every server
     * 
     * @param APIKey API Key to use
     * @param user Username to check
     * @return Integer Number of notes
     * @throws NetworkException
     * @throws APIException 
     */
    public Integer getNoteCount(String host, String APIKey, String user) throws NetworkException, APIException {
        return getTypeCount(host, APIKey, "Note", user);
    }

    /**
     * Returns a list of all the bans that a user has
     * 
     * @param APIKey API Key to use
     * @param user Username to check
     * @return List of UserBan objects
     * @throws NetworkException
     * @throws APIException 
     */
    public List<UserBan> getBans(String host, String APIKey, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL(host, APIKey, "getBans", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                List<UserBan> bans = new ArrayList<UserBan>();

                for (MapNode node : json.getMapNodeList("data")) {
                    bans.add(new UserBan(node));
                }
                return bans;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Returns a list of all the bans that an IP has
     * 
     * @param APIKey API Key to use
     * @param ip IP to check
     * @return List of IPBan objects
     * @throws NetworkException
     * @throws APIException 
     */
    public List<IPBan> getIPBans(String host, String APIKey, String ip) throws NetworkException, APIException {
        Response response = this.getAPIURL(host, APIKey, "getIPBans", "apiUNIQKey", ip);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                List<IPBan> bans = new ArrayList<IPBan>();

                for (MapNode node : json.getMapNodeList("data")) {
                    bans.add(new IPBan(node));
                }
                return bans;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Returns a list of all the notes that a user has
     * 
     * @param APIKey API Key to use
     * @param user Username to check
     * @return List of UserNote objects
     * @throws NetworkException
     * @throws APIException 
     */
    public List<UserNote> getNotes(String host, String APIKey, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL(host, APIKey, "getNotes", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                List<UserNote> bans = new ArrayList<UserNote>();

                for (MapNode node : json.getMapNodeList("data")) {
                    bans.add(new UserNote(node));
                }
                return bans;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Update the last login time of a username
     * 
     * @param APIKey API Key to use
     * @param user Username to update
     * @param ip IP address the user logged in with
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean updateUser(String host, String APIKey, String user, String ip) throws NetworkException, APIException {
        Response response = this.getAPIURL("updateUser", "apiUNIQKey", user, ip);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return true;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Returns the reason for something on this server only
     * 
     * @param APIKey API Key to use
     * @param type Type of item to get: Ban, IPBan, or Note
     * @param user Username to check
     * @return Reason for item
     * @throws NetworkException
     * @throws APIException 
     */
    protected String getReason(String host, String APIKey, String type, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL("get" + type + "Reason", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return json.getString("reason");
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Gets the reason for a user's ban on this server.
     * If the user is not banned, it throws an APIException
     * 
     * @param APIKey API Key to use
     * @param user Username to check
     * @return Ban reason
     * @throws NetworkException
     * @throws APIException 
     */
    public String getBanReason(String host, String APIKey, String user) throws NetworkException, APIException {
        return getReason(host, APIKey, "Ban", user);
    }

    /**
     * Gets the reason for an IP's ban on this server.
     * If the IP is not banned, it throws an APIException
     * 
     * @param APIKey API Key to use
     * @param ip IP to check
     * @return Ban reason
     * @throws NetworkException
     * @throws APIException 
     */
    public String getIPBanReason(String host, String APIKey, String ip) throws NetworkException, APIException {
        return getReason(host, APIKey, "IPBan", ip);
    }

    /**
     * Checks if the user/IP is banned.
     * 
     * @param APIKey API Key to use
     * @param type Type of ban
     * @param user Username/IP to check
     * @return Ban status
     * @throws NetworkException
     * @throws APIException 
     */
    protected boolean isBanned(String host, String APIKey, String type, String user) throws NetworkException, APIException {
        Response response = this.getAPIURL(host, APIKey, "get" + type + "Reason", "apiUNIQKey", user);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return json.getBoolean("is_banned", false);
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Checks if the username is banned on this server
     * 
     * @param APIKey API Key to use
     * @param user Username to check
     * @return Whether or not the user is banned
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean isBanned(String host, String APIKey, String user) throws NetworkException, APIException {
        return isBanned(host, APIKey, "Ban", user);
    }

    /**
     * Checks if the IP is banned on this server
     * 
     * @param APIKey API Key to use
     * @param ip IP to check
     * @return Whether or not the user is banned
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean isIPBanned(String host, String APIKey, String ip) throws NetworkException, APIException {
        return isBanned(host, APIKey, "IPBan", ip);
    }

    /**
     * Removes something from the server
     * 
     * @param APIKey API Key to use
     * @param type Type of thing to remove. Can be Ban, IPBan, Note
     * @param first User/IP to unban, or note issuer
     * @param second Note ID, if type == note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    protected boolean removeSomething(String host, String APIKey, String type, String first, String second) throws NetworkException, APIException {
        Response response = null;
        if (second != null) {
            response = this.getAPIURL(host, APIKey, "remove" + type, "apiUNIQKey", first, second);
        } else {
            response = this.getAPIURL(host, APIKey, "remove" + type, "apiUNIQKey", first);
        }

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return true;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Removes a ban for this user on this server
     * 
     * @param APIKey API Key to use
     * @param user Username to unban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean removeBan(String host, String APIKey, String user) throws NetworkException, APIException {
        return removeSomething(host, APIKey, "Ban", user, null);
    }

    /**
     * Removes a ban for this IP on this server
     * 
     * @param APIKey API Key to use
     * @param ip IP to unban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean removeIPBan(String host, String APIKey, String ip) throws NetworkException, APIException {
        return removeSomething(host, APIKey, "IPBan", ip, null);
    }

    /**
     * Removes a note with this NoteID.
     * 
     * @param APIKey API Key to use
     * @param id Note ID to remove
     * @param issuer User who issued the removeNote command
     * @return Whether or not the request succeeded.
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean removeNote(String host, String APIKey, Integer id, String issuer) throws NetworkException, APIException {
        return removeSomething(host, APIKey, "Note", issuer, id.toString());
    }

    /**
     * Adds something to the server
     * 
     * @param APIKey API Key to use
     * @param type Type of item to add. Ban, IPBan, GlobalNote, or Note
     * @param issuer User issuing the addition
     * @param user User to ban/note
     * @param reason Reason for the ban, or the text of the note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    protected boolean addSomething(String host, String APIKey, String type, String issuer, String user, String reason) throws NetworkException, APIException {
        Response response = this.getAPIURL(host, APIKey, "add" + type, "apiUNIQKey", issuer, user, reason);

        if (response.getContent() != null && response.getContent().length() != 0) {
            JSONNode json = null;
            try {
                json = response.getJSONResult();
            } catch (ParserException e) {
                throw new APIException("No JSON received! Is MCBouncer down?");
            }

            if (json.getBoolean("success", false)) {
                return true;
            } else {
                throw new APIException(json.getString("error"));
            }
        }
        throw new APIException("No content received! Is MCBouncer down?");
    }

    /**
     * Adds a ban for a username
     * 
     * @param APIKey API Key to use
     * @param issuer Person who issued the ban
     * @param user Username to ban
     * @param reason Reason for the ban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addBan(String host, String APIKey, String issuer, String user, String reason) throws NetworkException, APIException {
        return addSomething(host, APIKey, "Ban", issuer, user, reason);
    }

    /**
     * Adds a ban for an IP
     * 
     * @param APIKey API Key to use
     * @param issuer Person who issued the ban
     * @param ip IP to ban
     * @param reason Reason for the ban
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addIPBan(String host, String APIKey, String issuer, String ip, String reason) throws NetworkException, APIException {
        return addSomething(host, APIKey, "IPBan", issuer, ip, reason);
    }

    /**
     * Adds a note for a username
     * 
     * @param APIKey API Key to use
     * @param issuer Person who issued the note
     * @param user Username to note
     * @param note Text of the note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addNote(String host, String APIKey, String issuer, String user, String note) throws NetworkException, APIException {
        return addSomething(host, APIKey, "Note", issuer, user, note);
    }

    /**
     * Adds a global note for a username
     * 
     * @param APIKey API Key to use
     * @param issuer Person who issued the note
     * @param user Username to note
     * @param note Text of the note
     * @return Whether or not the request succeeded
     * @throws NetworkException
     * @throws APIException 
     */
    public boolean addGlobalNote(String host, String APIKey, String issuer, String user, String note) throws NetworkException, APIException {
        return addSomething(host, APIKey, "GlobalNote", issuer, user, note);
    }

    /**
     * Returns the last error that was received
     * from the API.
     * 
     */
    public String getLastError() {
        return lastError;
    }
    
    /**
     * Returns the input string with Minecraft color codes removed.
     * This code is taken from CraftBukkit ChatColor.stripColor.
     * @param input The input string to remove color codes from.
     * @return The input string void of color codes.
     */
    public String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return input.replaceAll("(?i)\u00A7[0-F]", "");
    }
}
