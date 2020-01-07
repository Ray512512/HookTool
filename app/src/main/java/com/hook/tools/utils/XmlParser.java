package com.hook.tools.utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class XmlParser {
    final XmlPullParser a;

    public XmlParser(XmlPullParser xmlPullParser) {
        this.a = xmlPullParser;
    }

    public static XmlParser a() {
        return new XmlParser(Xml.newPullParser());
    }

    /* access modifiers changed from: 0000 */
    public String a(String str) {
        return this.a.getAttributeValue(null, str);
    }

    /* access modifiers changed from: 0000 */
    public void a(InputStream inputStream, boolean z) throws XmlPullParserException, IOException {
      /*  if (!z) {
            this.a.setInput(inputStream, "UTF-8");
            return;
        }
        this.a.setInput(new StringReader(new String(a.b(b.a(inputStream)))));*/
        this.a.setInput(inputStream, "UTF-8");
        print();
    }

    /* access modifiers changed from: 0000 */
    public int b() throws XmlPullParserException, IOException {
        return this.a.next();
    }

    /* access modifiers changed from: 0000 */
    public String c() {
        return this.a.getName();
    }

    /* access modifiers changed from: 0000 */
    public String d() throws XmlPullParserException, IOException {
        return this.a.nextText();
    }
    public void print() throws IOException, XmlPullParserException {
        boolean z2 = true;
        do {
            String lowerCase = c().toLowerCase();
            LogUtils.show("lowerCase:"+lowerCase);
            switch (b()) {
                case 1:
                    z2 = false;
                    continue;
                case 2:
                     lowerCase = c().toLowerCase();
                    if ("widget".equals(lowerCase)) {
                        LogUtils.show("widget:"+a("id"));
                        LogUtils.show("widget:"+a("version"));
                        LogUtils.show("widget:"+a("security"));
                        LogUtils.show("widget:"+a("loader"));
                        continue;
                    } else if ("name".equals(lowerCase)) {
                        LogUtils.show("name:"+a("id")+"&"+d());
                        continue;
                    } else if ("description".equals(lowerCase)) {
                        LogUtils.show("description:"+a(d()));
                        continue;
                    } else if ("author".equals(lowerCase)) {
                        LogUtils.show("author:"+a("email"));
                        LogUtils.show("author:"+a("href"));
                        LogUtils.show("author:"+d());
                        continue;
                    } else if ("content".equals(lowerCase)) {
                        LogUtils.show("content:"+a("src"));
                        continue;
                    } else if ("access".equals(lowerCase)) {
                        LogUtils.show("access:"+a("origin"));
                        continue;
                    } else if ("preference".equals(lowerCase)) {
                        LogUtils.show("preference:"+a("value"));
                        continue;
                    } else if ("permission".equals(lowerCase)) {
                        LogUtils.show("permission:"+a("name"));
                        continue;
                    } else if ("feature".equals(lowerCase)) {
                        LogUtils.show("feature:"+a("name"));
                        continue;
                    } else if ("param".equals(lowerCase)) {
                        LogUtils.show("param:"+a("value"));
                        continue;
                    } else {
                        continue;
                    }
                    default:
            }
        } while (z2);

    }
}