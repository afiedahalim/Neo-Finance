package com.example.vehicleloan;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    public AboutFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_about, container, false);

        // fragment enter animation
        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        root.startAnimation(fadeIn);

        ImageView ivLogo = root.findViewById(R.id.company_logo);
        TextView tvAuthor = root.findViewById(R.id.about_author);
        TextView tvAuthorName = root.findViewById(R.id.about_author_name);
        TextView tvAuthorId = root.findViewById(R.id.about_author_id);
        TextView tvAuthorCourse = root.findViewById(R.id.about_author_course);
        TextView tvCopy = root.findViewById(R.id.about_copyright);
        TextView tvGit = root.findViewById(R.id.about_github);
        TextView tvContact = root.findViewById(R.id.about_contact);
        TextView tvVersion = root.findViewById(R.id.about_version);

        // Set company logo (fallback if not found)
        int logoRes = getResources().getIdentifier(
                "company_logo", "drawable", requireActivity().getPackageName()
        );
        ivLogo.setImageResource(logoRes != 0 ? logoRes : R.mipmap.ic_launcher);
        // ensure the rounded background is respected
        ivLogo.setClipToOutline(true);

        // Set text from strings.xml
        tvAuthor.setText(getString(R.string.about_author));
        tvAuthorName.setText(getString(R.string.about_author_name));
        tvAuthorId.setText(getString(R.string.about_author_id));
        tvAuthorCourse.setText(getString(R.string.about_author_course));
        tvCopy.setText(getString(R.string.about_copyright));

        // App version
        try {
            PackageInfo pi = requireContext()
                    .getPackageManager()
                    .getPackageInfo(requireContext().getPackageName(), 0);
            tvVersion.setText(getString(R.string.about_version_text, pi.versionName));
        } catch (Exception ignored) { }

        // GitHub link (clickable, underlined)
        final String url = getString(R.string.github_url);

        // Use Html so it shows as a clickable link; the onClick fallback also opens browser
        tvGit.setText(Html.fromHtml("<a href=\"" + url + "\">" + url + "</a>"));
        tvGit.setMovementMethod(LinkMovementMethod.getInstance());
        tvGit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        // Contact email
        tvContact.setOnClickListener(v -> {
            String email = getString(R.string.about_contact_email);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));

            if (emailIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                startActivity(emailIntent);
            }
        });

        return root;
    }
}