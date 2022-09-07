import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/common/product';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-list',
  //templateUrl: './product-list-table.component.html',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[];
  currentCategoryId: number;
  currentCategoryName: string |  null;
  searchMode: boolean;

  constructor(private productService: ProductService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts() {

    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if(this.searchMode){
      this.handleSearchProducts();
    }
    else{
      this.handleListProduct();
    }
    
   
  }
  handleSearchProducts() {
    const theKeyword = this.route.snapshot.paramMap.get('keyword');

    this.productService.searchProducts(theKeyword).subscribe(
      data => {
        this.products = data;
      }
    );
  }

  handleListProduct(){

     // check if "id" parameter is available
     const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');

     if (hasCategoryId) {
       // get the "id" param string. convert string to a number using the "+" symbol
       this.currentCategoryId = +this.route.snapshot.paramMap.getAll('id');
 
       // get the "name" param string
       this.currentCategoryName = this.route.snapshot.paramMap.get('name');
     }
     else {
       // not category id available ... default to category id 1
       this.currentCategoryId = 1;
       this.currentCategoryName = 'Books';
     }
 
     // now get the products for the given category id
     this.productService.getProductList(this.currentCategoryId).subscribe(
       data => {
         this.products = data;
       }
     );

  }


}
