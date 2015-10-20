/*
 * Copyright (c) 2014 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

'use strict';

mica.network
  .config(['$routeProvider',
    function ($routeProvider) {
      $routeProvider
        .when('/network', {
          templateUrl: 'app/network/views/network-list.html',
          controller: 'NetworkListController'
        })
        .when('/network/new', {
          templateUrl: 'app/network/views/network-form.html',
          controller: 'NetworkEditController'
        })
        .when('/network/:id', {
          templateUrl: 'app/network/views/network-view.html',
          controller: 'NetworkViewController'
        })
        .when('/network/:id/edit', {
          templateUrl: 'app/network/views/network-form.html',
          controller: 'NetworkEditController'
        })
        .when('/network/:id/revisions', {
          templateUrl: 'app/network/views/network-view-revisions.html',
          controller: 'NetworkViewController'
        });
    }]);
