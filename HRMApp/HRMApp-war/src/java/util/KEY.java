/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author heymeowcat
 */

public class KEY {
                public String secretKey = DigestUtils.md5Hex("heymeowcatHRM!");
}
